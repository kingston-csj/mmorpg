package org.forfun.mmorpg.game.skill.service

import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.scene.actor.Creature

/**
 * 敌对策略目标选择器
 */
sealed class SkillRelationSelector {

    abstract fun select(context: BattleContext, pool: List<Creature>): List<Creature>

    object Friend : SkillRelationSelector() {
        override fun select(context: BattleContext, pool: List<Creature>): List<Creature> {
            return pool.filter { it.type == ActorType.Player }
        }
    }

    object Enemy : SkillRelationSelector() {
        override fun select(context: BattleContext, pool: List<Creature>): List<Creature> {
            return pool.filter { it.type == ActorType.Monster }
        }
    }

    object All : SkillRelationSelector() {
        override fun select(context: BattleContext, pool: List<Creature>): List<Creature> {
            return pool
        }

    }

    companion object {
        fun getByType(type: String): SkillRelationSelector {
            return when (type) {
                "Enemy" -> Enemy
                "Friend" -> Friend
                else -> return All
            }
        }
    }
}