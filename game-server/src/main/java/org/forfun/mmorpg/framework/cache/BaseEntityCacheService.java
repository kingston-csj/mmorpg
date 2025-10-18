package org.forfun.mmorpg.framework.cache;


import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jforgame.commons.util.StringUtil;
import org.forfun.mmorpg.game.base.EntityCacheService;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import jforgame.commons.persist.DbService;

/**
 * 抽象缓存服务，提供缓存功能
 * 子类只需实现 {@link #getEntityClass}方法即可
 *
 * @param <E>
 * @param <ID>
 */
public abstract class BaseEntityCacheService<E extends BaseEntity<ID>, ID extends Comparable<ID> & Serializable> implements EntityCacheService<E, ID> {

    @Autowired
    DbService dbService;

    // 分段锁
    private final Lock[] locks = new Lock[10];

    public BaseEntityCacheService() {
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new ReentrantLock();
        }
    }

    /**
     * 获取缓存对应名称
     *
     * @return
     */
    protected String getCacheName() {
        return getEntityClass().getSimpleName().toLowerCase();
    }

    protected String getBeanName() {
        return getEntityClass().getSimpleName() + "CacheService";
    }

    @Override
    @Cacheable(cacheResolver = "entityCacheResolver", key = "#p0")
    public E getEntity(ID id) {
        // 命名约定: PlayerEnt -> playerDao
        String repositoryBeanName = getEntityClass().getSimpleName().replace("Ent", "Dao");
        repositoryBeanName = StringUtil.firstLetterToLowerCase(repositoryBeanName);
        JpaRepository<E, ID> repository = GameContext.getBean(repositoryBeanName, JpaRepository.class);

        return repository.findById(id).orElse(null);
    }

    @Override
    @CachePut(cacheResolver = "entityCacheResolver", key = "#entity.id")
    public E putEntity(E entity) {
        // 使用实体自身的 getCrudRepository 方法获取对应的 Repository
        JpaRepository<E, ID> repository = (JpaRepository<E, ID>) entity.getCrudRepository();
        
        // 使用 JpaRepository 保存实体
        E savedEntity = repository.save(entity);
        return savedEntity;
    }

    @Override
    @CacheEvict(cacheResolver = "entityCacheResolver", key = "#p0.id")
    public void removeEntity(E entity) {
        // 使用实体自身的 getCrudRepository 方法获取对应的 Repository
        JpaRepository<E, ID> repository = (JpaRepository<E, ID>) entity.getCrudRepository();
        
        // 使用 JpaRepository 删除实体
        repository.delete(entity);
    }

    @SuppressWarnings("all")
    public E getOrCreate(ID id, EntityBuilder<E> builder) {
        // spring cache内部调用方法，是不会触发aop缓存的
        // 避免内部方法调用，确保缓存生效
        EntityCacheService self = GameContext.getBean(getBeanName(), BaseEntityCacheService.class);
        E entity = (E) self.getEntity(id);
        if (entity == null) {
            int index = Math.abs(id.hashCode()) % locks.length;
            Lock lock = locks[index];
            lock.lock();
            try {
                entity = (E) self.getEntity(id);
                if (entity == null) {
                    entity = builder.build();
                    self.putEntity(entity);
                }
            } finally {
                lock.unlock();
            }
        }
        return entity;
    }

    /**
     * 由于泛型擦除，子类无法获取到泛型类型，因此需要子类实现该方法，返回实体类的Class对象
     * 获取实体对应的Class对象
     *
     * @return
     */
    protected abstract Class<E> getEntityClass();
    


}
