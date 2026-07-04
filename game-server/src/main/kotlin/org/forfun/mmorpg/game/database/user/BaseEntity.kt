package org.forfun.mmorpg.game.database.user

import jforgame.commons.persist.Entity
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

interface BaseEntity<ID> : Entity<ID>
        where ID : Serializable,
              ID : Comparable<ID> {

    /**
     * springdata jpa没有类似MongoTemplate的工具，只能手动绑定Entity与CrudRepository
     * @return
     */
    fun getCrudRepository(): CrudRepository<out BaseEntity<ID>, ID>

    override fun getKey(): String {
        return this::class.java.simpleName + "@" + getId().toString()
    }

    /**
     * 大部分表数据都无需删除，特殊需求，子类重写该方法
     * @return 是否标记未删除状态
     */
    fun isDelete(): Boolean {
        return false
    }
}
