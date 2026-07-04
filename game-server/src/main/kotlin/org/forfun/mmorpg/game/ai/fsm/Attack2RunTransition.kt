package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

class Attack2RunTransition(from: State, to: State) : Transition(from, to) {

    override fun meetCondition(creature: Creature): Boolean {
        // 如果当前在攻击状态，且攻击力比怪物低，那就赶紧逃命吧
        val player = creature as PlayerEnt
        val scene = player.scene
        return player.hp < 50 // 快死啦
                || player.attack > scene?.monster!!.attack || Math.random() < 0.4 // 有概率逃跑，增大随机事件
    }
}
