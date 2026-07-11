package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.DataTable
import jforgame.data.annotation.Id

@DataTable(name = "configskill")
class ConfigSkill {

    @Id
    var id: Int = 0
    var name: String? = null
    var needLevel: Int = 0

    /**
     * 最大攻击距离
     */
    var distance: Int = 0

    /**
     * 选择区域范围
     */
    var selector: String = ""

    var enemyRelation: String = ""

    /**
     * 释放间隔毫秒数
     */
    var cd: Int = 0

    /**
     * aoe群攻数量
     */
    var aoeRange: Int = 0

    /**
     * 技能效果说明
     */
    var effect: String? = null

    /**
     * 技能类型：DAMAGE(伤害), HEAL(治疗), BUFF(增益), DEBUFF(减益), SPECIAL(特殊)
     */
    var skillType: String = "DAMAGE"

    /**
     * 伤害类型：PHYSICAL(物理), MAGIC(魔法), TRUE(真实伤害)
     */
    var damageType: String = "PHYSICAL"

    /**
     * 伤害系数（万分比），基础伤害 = 攻击 * damageCoeff / 10000
     */
    var damageCoeff: Int = 10000

    /**
     * 固定伤害值
     */
    var fixedDamage: Int = 0

    /**
     * 治疗系数（万分比），基础治疗 = 攻击 * healCoeff / 10000
     */
    var healCoeff: Int = 0

    /**
     * 固定治疗值
     */
    var fixedHeal: Int = 0

    /**
     * 技能释放后，对自己施加的buff列表（逗号分隔的buffId）
     */
    var selfBuffIds: String = ""

    /**
     * 技能释放后，对命中目标施加的buff列表（逗号分隔的buffId）
     */
    var targetBuffIds: String = ""
}