package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


class Run2PatrolTransition(from: State, to: State) : Transition(from, to) {

    override fun meetCondition(creature: Creature): Boolean {
        // 逃跑后肯定是巡逻啦
        return true
    }
}
