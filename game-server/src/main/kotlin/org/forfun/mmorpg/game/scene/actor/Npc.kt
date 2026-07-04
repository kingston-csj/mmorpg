package org.forfun.mmorpg.game.scene.actor

class Npc : NonLiving() {

    override val type: ActorType
        get() = ActorType.Npc
    override fun getId(): Long {
        return 0
    }
}
