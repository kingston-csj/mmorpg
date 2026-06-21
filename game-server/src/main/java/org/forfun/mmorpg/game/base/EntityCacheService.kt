package org.forfun.mmorpg.game.base

import org.forfun.mmorpg.game.database.user.BaseEntity
import java.io.Serializable

interface EntityCacheService<E : BaseEntity<ID>, ID>
        where ID : Serializable,
              ID : Comparable<ID> {

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    fun getEntity(id: ID): E

    /**
     * 更新/插入实体
     * 若实体已存在于数据库，则执行更新操作；否则，执行插入操作
     * @param entity
     * @return
     */
    fun putEntity(entity: E): BaseEntity<ID>

    /**
     * 移除实体
     * @param entity
     */
    fun removeEntity(entity: E) {
        throw UnsupportedOperationException()
    }
}
