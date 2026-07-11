package org.forfun.mmorpg.game.buff.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.buff.model.Buff
import org.forfun.mmorpg.game.database.config.domain.ConfigBuff
import org.springframework.stereotype.Service

@Service
open class BuffService {

    fun addBuff(target: Creature, buffId: Int, caster: Creature) {
        val config = GameContext.getDataManager().queryById(ConfigBuff::class.java, buffId)
            ?: return

        val existingBuff = target.buffContainer.getBuff(config.id)
        if (existingBuff != null) {
            if (config.isOverlap) {
                existingBuff.overlapLayer++
                existingBuff.duration = config.duration
            } else {
                existingBuff.duration = config.duration
                existingBuff.registerTimeOutTask()
            }
            return
        }

        val buff = createBuff(config)
        buff.owner = target
        buff.origin = caster
        buff.duration = config.duration
        target.buffContainer.addBuff(buff)
        buff.init()
    }

    fun removeBuff(owner: Creature, buff: Buff): Buff? {
        return owner.buffContainer.removeBuff(buff)
    }

    fun removeBuff(owner: Creature, buffId: Int): Buff? {
        return owner.buffContainer.removeBuff(buffId)
    }

    private fun createBuff(config: ConfigBuff): Buff {
        return object : Buff() {
            init {
                modelId = config.id
                duration = config.duration
            }
        }
    }
}