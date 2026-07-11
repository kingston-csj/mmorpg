package org.forfun.mmorpg.game.scene.actor


open class Monster : Creature() {

    var modelId: Int = 0

    var name: String? = null

    override val type: ActorType
        get() = ActorType.Monster

    override fun getId(): Long {
        return 0
    }
}
