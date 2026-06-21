package org.forfun.mmorpg.game.scene.actor

import org.forfun.mmmorpg.game.scene.actor.Creature

open class Monster : Creature() {

    var modelId: Int = 0

    var name: String? = null

    override val type: ActorType
        get() = ActorType.Monster

    override fun getId(): Long {
        return 0
    }
}
