package org.forfun.mmorpg.game.database.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.BaseEntity
import org.forfun.mmorpg.game.database.user.dao.IdentityDao
import org.springframework.data.repository.CrudRepository
import java.util.concurrent.atomic.AtomicLong

@Entity
@Table(name = "identityent")
class IdentityEnt : BaseEntity<Int> {

    @Id
    @Column
    var id: Int = 0
    @Column
    var value: Long = 0

    var factory: AtomicLong? = null

    override fun getId(): Int {
        return id
    }

    override fun getCrudRepository(): CrudRepository<IdentityEnt, Int> {
        return GameContext.getBean(IdentityDao::class.java)
    }
}
