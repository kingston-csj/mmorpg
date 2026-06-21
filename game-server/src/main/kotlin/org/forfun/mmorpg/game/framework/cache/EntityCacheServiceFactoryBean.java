package org.forfun.mmorpg.game.framework.cache;


import org.forfun.mmorpg.framework.cache.BaseEntityCacheService;
import org.forfun.mmorpg.framework.cache.EntityCacheServiceFactory;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// 使用具体的 FactoryBean 实现，而非泛型接口
public class EntityCacheServiceFactoryBean implements FactoryBean<org.forfun.mmorpg.framework.cache.BaseEntityCacheService<?, ?>>, ApplicationContextAware {

    private final Class<? extends BaseEntity<?>> entityClass;
    private ApplicationContext applicationContext;
    private org.forfun.mmorpg.framework.cache.BaseEntityCacheService<?, ?> cachedInstance;

    public EntityCacheServiceFactoryBean(Class<? extends BaseEntity<?>> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public org.forfun.mmorpg.framework.cache.BaseEntityCacheService<?, ?> getObject() {
        if (cachedInstance == null) {
            // 延迟获取工厂实例，确保ApplicationContext已经初始化
            org.forfun.mmorpg.framework.cache.EntityCacheServiceFactory factory = applicationContext.getBean(EntityCacheServiceFactory.class);
            cachedInstance = factory.create(entityClass);
        }
        return cachedInstance;
    }

    @Override
    public Class<?> getObjectType() {
        return BaseEntityCacheService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}