package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


class PatrolState : State {

    override fun onEnter(creature: Creature) {
        // TODO Auto-generated method stub
    }

    override fun onExit(creature: Creature) {
        // TODO Auto-generated method stub
    }

    override fun execute(creature: Creature) {
        System.err.println("大王叫我来寻山，寻完南山寻北山")
    }
}
