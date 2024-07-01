package org.forfun.mmorpg.game.base;

import org.forfun.mmorpg.game.database.user.BaseEntity;

import java.io.Serializable;

public interface EntityCacheService<E extends BaseEntity<ID>, ID extends Serializable & Comparable<ID>> {


    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    E getEntity(ID id);

    /**
     * 更新/插入实体
     * 若实体已存在于数据库，则执行更新操作；否则，执行插入操作
     * @param entity
     * @return
     */
    BaseEntity<ID> putEntity(E entity);

    /**
     * 移除实体
     * @param id
     * @return
     */
    default BaseEntity<ID> removeEntity(ID id) {
        throw new UnsupportedOperationException();
    }
}
