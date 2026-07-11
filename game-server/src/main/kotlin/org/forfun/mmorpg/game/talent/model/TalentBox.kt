package org.forfun.mmorpg.game.talent.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Transient
import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.talent.TalentEffect

class TalentBox {

    /**
     * 天赋id -> level
     */
    var talentLevels: MutableMap<Int, Int> = HashMap()

    /**
     * 触发点 -> 天赋效果列表（学习天赋时缓存，避免触发时重复查询）
     */
    @Transient
    @JsonIgnore
    var triggerEffect: MutableMap<TriggerPoint, MutableList<TalentEffectWrapper>> = HashMap()

    fun hasTalent(talentId: Int): Boolean {
        return talentLevels.containsKey(talentId)
    }

    /**
     * 添加天赋并缓存其效果
     */
    fun addTalent(talent: ConfigTalent) {
        val effectConfigs = talent.getEffectConfigs()
        for (config in effectConfigs) {
            val effect = org.forfun.mmorpg.game.talent.TalentEffectRegistry.getEffect(config.effectType)
            if (effect != null) {
                val wrappers = triggerEffect.computeIfAbsent(config.triggerPoint) { mutableListOf() }
                wrappers.add(TalentEffectWrapper(effect, talent, config))
            }
        }
    }

    /**
     * 移除天赋效果
     */
    fun removeTalent(talentId: Int) {
        triggerEffect.values.forEach { wrappers ->
            wrappers.removeIf { it.talent.id == talentId }
        }
        triggerEffect = triggerEffect.filterValues { it.isNotEmpty() }.toMutableMap()
    }

    /**
     * 触发指定触发点的所有天赋效果
     */
    fun trigger(triggerPoint: TriggerPoint, context: BattleContext) {
        val effects = triggerEffect[triggerPoint] ?: return
        for (wrapper in effects) {
            wrapper.effect.execute(context, wrapper.talent, wrapper.effectConfig)
        }
    }

    /**
     * 清空所有天赋效果缓存
     */
    fun clearEffects() {
        triggerEffect.clear()
    }

    /**
     * 天赋效果包装类，包含效果实例、天赋配置和效果配置
     */
    data class TalentEffectWrapper(
        val effect: TalentEffect,
        val talent: ConfigTalent,
        val effectConfig: ConfigTalent.TalentEffectConfig
    )
}