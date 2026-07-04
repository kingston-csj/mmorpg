package org.forfun.mmorpg.game.buff.service

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.buff.model.Buff
import org.springframework.stereotype.Service

@Service
open class BuffService {

    fun removeBuff(owner: Creature, buff: Buff): Buff? {
        return null
    }
}
