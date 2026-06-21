package org.forfun.mmorpg.game.database.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.BaseEntity
import org.forfun.mmorpg.game.database.user.dao.AccountDao
import org.hibernate.annotations.Proxy
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "accountent")
@Proxy(lazy = false)
class AccountEnt : BaseEntity<Long> {

    @Id
    @Column
    var id: Long = 0

    @Column
    var name: String? = null

    override fun getCrudRepository(): CrudRepository<AccountEnt, Long> {
        return GameContext.getBean(AccountDao::class.java)
    }

    constructor()

    override fun getId(): Long? {
        return id;
    }
}
