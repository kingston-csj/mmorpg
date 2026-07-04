package org.forfun.mmorpg.game.fight.model

import org.forfun.mmorpg.game.scene.actor.SceneActor
import java.util.LinkedList

/**
 * 每个主动技能对应的上下文
 */
class BattleContext {

    var skillId: Int

    /**
     * 攻击者
     */
    lateinit var attacker: SceneActor

    /**
     * 所有命中目标
     */
    var targets = LinkedList<SceneActor>()

    constructor(id: Int) {
        skillId = id
    }

    fun addTarget(target: SceneActor) {
        targets.add(target)
    }

}