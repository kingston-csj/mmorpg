package org.forfun.mmorpg.game.skill.model

class SkillEntry {

    var skillId: Int = 0

    /**
     * 伤害值
     */
    var damage: Int = 0

    /**
     * 触发暴击
     */
    var criticalHit: Boolean = false;

    /**
     * 触发暴击
     */
    var excellentHit: Boolean = false
}