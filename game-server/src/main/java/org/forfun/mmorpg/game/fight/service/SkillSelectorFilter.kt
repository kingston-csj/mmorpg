package org.forfun.mmorpg.game.fight.service

import org.forfun.mmorpg.game.fight.model.BattleContext
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.scene.actor.SceneActor

sealed class SkillSelectorFilter {

    abstract fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor>

    object Friend : SkillSelectorFilter() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            return pool.filter { it.type == ActorType.Player }
        }
    }

    object Enemy : SkillSelectorFilter() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            return pool.filter { it.type == ActorType.Monster }
        }
    }

    object All : SkillSelectorFilter() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            return pool
        }

    }

    companion object {
        /**
         * 根据类型编码获取对应范围筛选器
         * type 1=圆形 Circle，type 2=扇形 Sector
         */
        fun getByType(type: String): SkillSelectorFilter {
            return when (type) {
                "Enemy" -> Enemy
                "Friend" -> Friend
                else -> return All
            }
        }
    }
}