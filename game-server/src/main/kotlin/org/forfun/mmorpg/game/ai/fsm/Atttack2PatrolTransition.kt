package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

class Atttack2PatrolTransition(from: State, to: State) : Transition(from, to) {

    override fun meetCondition(creature: Creature): Boolean {
        val player = creature as PlayerEnt
        val scene = player.scene
        val monster = scene?.monster

        return monster!!.isDie
    }
}
