package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


class RunAwayState : State {

    override fun onEnter(creature: Creature) {
        // TODO Auto-generated method stub
    }

    override fun onExit(creature: Creature) {
        // TODO Auto-generated method stub
    }

    override fun execute(creature: Creature) {
        System.err.println("三十六计，走为上计")
    }
}
