package org.forfun.mmorpg.game.database.user.entity

import jakarta.persistence.*
import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.attribute.AttributeContainer
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.buff.model.BuffContainer
import org.forfun.mmorpg.game.database.converter.JpaObjectConverter
import org.forfun.mmorpg.game.database.user.BaseEntity
import org.forfun.mmorpg.game.database.user.dao.PlayerDao
import org.forfun.mmorpg.game.vip.model.VipRight
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.scene.model.Life
import org.hibernate.annotations.Proxy
import org.springframework.data.repository.CrudRepository

@Entity
@Table(name = "playerent")
@Proxy(lazy = false)
// 默认采用 FIELD 访问，确保 vipRight 上的 @field:Convert 生效（否则 Hibernate 会把
// VipRight 当成原生列类型，抛 Could not determine recommended JdbcType）
@Access(AccessType.FIELD)
class PlayerEnt() : Creature(), BaseEntity<Long> {

    init {
        buffContainer = BuffContainer()
        attrContainer = AttributeContainer()
    }

    fun setLife(health: Long, mana: Long) {
        life = Life(health, mana)
    }

    /**
     * 所属账号id
     */
    @field:Column
    var accountId: Long = 0

    @field:Column
    var name: String? = null

    @field:Column
    var level: Int = 0

    @field:Column
    private var _id: Long = 0

    @field:Column
    @field:Convert(converter = JpaObjectConverter::class)
    var vipRight: VipRight? = null


    @Transient
    override fun getCrudRepository(): CrudRepository<PlayerEnt, Long> {
        return GameContext.getBean(PlayerDao::class.java)
    }

    @Id
    @Column
    @Access(AccessType.PROPERTY)
    override fun getId(): Long {
        return _id
    }

    fun setId(id: Long) {
        _id = id
    }


    override val type: ActorType = ActorType.Player

    fun isOpenedFunction(funcId: Int): Boolean {
        return false
    }
}
