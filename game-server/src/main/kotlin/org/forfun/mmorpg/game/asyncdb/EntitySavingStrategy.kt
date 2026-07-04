package org.forfun.mmorpg.game.asyncdb

import jforgame.commons.persist.Entity
import jforgame.commons.persist.SavingStrategy
import org.forfun.mmorpg.game.database.user.BaseEntity
import org.springframework.data.repository.CrudRepository

class EntitySavingStrategy : SavingStrategy {

    @Throws(Exception::class)
    override fun doSave(entity: Entity<*>) {
        val baseEntity = entity as BaseEntity<*>
        // getCrudRepository() 声明为 CrudRepository<out BaseEntity<ID>, ID>，out 投影下
        // 无法直接调用 save/delete（参数类型会被投影成 Nothing），这里做一次不受检转换
        @Suppress("UNCHECKED_CAST")
        val repo = baseEntity.getCrudRepository() as CrudRepository<Entity<*>, Any?>
        if (baseEntity.isDelete()) {
            repo.delete(entity)
        } else {
            repo.save(entity)
        }
    }
}
