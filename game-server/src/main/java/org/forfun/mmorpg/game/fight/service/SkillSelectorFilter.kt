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

}