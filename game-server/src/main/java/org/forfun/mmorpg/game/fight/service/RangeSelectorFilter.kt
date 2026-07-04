package org.forfun.mmorpg.game.fight.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.fight.model.BattleContext
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.skill.util.AngleUtil

/**
 * 几何范围选择器
 */
sealed class RangeSelectorFilter {

    abstract fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor>

    /**
     * 圆形
     */
    object Circle : RangeSelectorFilter() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, context.skillId)
            val radius = skillData.distance.toDouble()
            val attacker = context.attacker
            return pool.filter { actor ->
                AngleUtil.isInCircle(attacker.posX, attacker.posY, actor.posX, actor.posY, radius)
            }
        }
    }

    /**
     * 扇形
     */
    object Sector : RangeSelectorFilter() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, context.skillId)
            val radius = skillData.distance.toDouble()
            val attacker = context.attacker
            val axisAngle = 0.0
            val extendAngle = 45.0
            return pool.filter { actor ->
                AngleUtil.isInSector(attacker.posX, attacker.posY, actor.posX, actor.posY, radius, axisAngle, extendAngle)
            }
        }
    }

}