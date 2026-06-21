package org.forfun.mmorpg.game.ai.monster

/**
 * 怪物的伤害统计
 */
class DamageStatistics {

    /**
     * 统计列表 攻击方id -> 伤害值
     */
    val damages = HashMap<Long, Long>()

    fun addDamage(attacker: Long, hurt: Long) {
        this.damages[attacker] = this.damages.getOrDefault(attacker, 0L) + hurt
    }

    fun getDamage(attacker: Long): Long {
        return damages.getOrDefault(attacker, 0L)
    }

    fun clearDamage() {
        this.damages.clear()
    }
}
