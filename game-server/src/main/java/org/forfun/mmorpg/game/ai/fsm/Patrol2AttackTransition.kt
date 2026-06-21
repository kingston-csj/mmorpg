package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

class Patrol2AttackTransition(from: State, to: State) : Transition(from, to) {

    override fun meetCondition(creature: Creature): Boolean {
        // 如果当前在巡逻状态，且怪物没死，就揍它
        val player = creature as PlayerEnt
        val scene = player.scene

        return !scene?.monster!!.isDie
    }
}
