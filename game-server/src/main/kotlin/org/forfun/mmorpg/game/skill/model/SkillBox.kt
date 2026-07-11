package org.forfun.mmorpg.game.skill.model

class SkillBox {

    /**
     * 技能id -> level
     */
    var skillLevels: MutableMap<Int, Int> = HashMap()

    /**
     * 技能id -> 上一次释放的时间
     */
    var cd: MutableMap<Int, Long> = HashMap()

    fun hasSkill(skillId: Int): Boolean {
        return skillLevels.containsKey(skillId)
    }
}