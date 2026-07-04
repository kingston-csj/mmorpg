package org.forfun.mmorpg.game.fight.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.fight.model.BattleContext
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.skill.util.AngleUtil

/**
 * 几何范围选择器
 */
sealed class SkillRangeSelector {

    abstract fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor>

    /**
     * 圆形
     */
    object Circle : SkillRangeSelector() {
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
    object Sector : SkillRangeSelector() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, context.skillId)
            val radius = skillData.distance.toDouble()
            val attacker = context.attacker
            val axisAngle = 0.0
            val extendAngle = 45.0
            return pool.filter { actor ->
                AngleUtil.isInSector(
                    attacker.posX,
                    attacker.posY,
                    actor.posX,
                    actor.posY,
                    radius,
                    axisAngle,
                    extendAngle
                )
            }
        }
    }

    object All : SkillRangeSelector() {
        override fun select(context: BattleContext, pool: List<SceneActor>): List<SceneActor> {
            return pool
        }

    }

    companion object {
        /**
         * 根据类型编码获取对应范围筛选器
         * type 1=圆形 Circle，type 2=扇形 Sector
         */
        fun getByType(type: String): SkillRangeSelector {
            return when (type) {
                "Circle" -> Circle
                "Sector" -> Sector
                else -> return All
            }
        }
    }


}