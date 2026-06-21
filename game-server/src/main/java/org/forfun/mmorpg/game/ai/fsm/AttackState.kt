package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.scene.actor.Monster

class AttackState : State {

    override fun onEnter(creature: Creature) {
        // 进入攻击状态
    }

    override fun onExit(creature: Creature) {
        // 离开攻击状态
    }

    override fun execute(creature: Creature) {
        val player = creature as PlayerEnt
        val scene = player.scene
        val monster = scene?.monster
        player.life?.reduceHp(monster!!.attack.toLong())
        monster?.life?.reduceHp((-player.attack).toLong())
        System.err.println(
            "邂逅敌人，快使用双截棍，哼哼哈兮。" + "我方血量[" + player.hp + "]" + "敌方血量[" + monster?.hp + "]"
        )
    }
}
