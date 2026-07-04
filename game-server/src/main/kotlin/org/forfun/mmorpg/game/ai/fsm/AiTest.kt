package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.model.Life

object AiTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val player = PlayerEnt()
        player.life = Life(150, 150)
        val monster = Monster()
        monster.life = Life(120, 120)

        val scene = Scene()
        scene.player = player
        scene.monster = monster

        player.scene = scene
        monster.scene = scene

        val patrolState = PatrolState()
        val attackState = AttackState()
        val runState = RunAwayState()

        val transition1 = Patrol2AttackTransition(patrolState, attackState)
        val transition2 = Attack2RunTransition(attackState, runState)
        val transition3 = Atttack2PatrolTransition(attackState, patrolState)
        val transition4 = Run2PatrolTransition(runState, patrolState)

        val fsm = FiniteStateMachine()
        fsm.initState = patrolState

        fsm.addTransition(transition1)
        fsm.addTransition(transition2)
        fsm.addTransition(transition3)
        fsm.addTransition(transition4)

        while (true) {
            fsm.enterFrame(player)
            Thread.sleep(500)
        }
    }
}
