package org.forfun.mmorpg.game.skill.service

import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.skill.talent.TalentEffectRegistry
import org.springframework.stereotype.Service

@Service
class TalentService {

    private val playerTalents = mutableMapOf<Long, MutableList<ConfigTalent>>()

    fun trigger(triggerPoint: TriggerPoint, context: BattleContext) {
        val attacker = context.attacker
        if (attacker is PlayerEnt) {
            val talents = playerTalents.getOrDefault(attacker.id, emptyList())
            for (talent in talents) {
                val effectConfigs = talent.getEffectConfigs()
                for (config in effectConfigs) {
                    if (config.triggerPoint == triggerPoint) {
                        val effect = TalentEffectRegistry.getEffect(config.effectType)
                        effect?.execute(context, talent, config)
                    }
                }
            }
        }
    }

    fun activateTalent(player: PlayerEnt, talentId: Int) {
        val talent = getTalentConfig(talentId) ?: return
        val talents = playerTalents.computeIfAbsent(player.id) { mutableListOf() }
        if (!talents.any { it.id == talentId }) {
            talents.add(talent)
        }
    }

    fun deactivateTalent(player: PlayerEnt, talentId: Int) {
        val talents = playerTalents[player.id] ?: return
        talents.removeIf { it.id == talentId }
    }

    fun getActiveTalents(player: PlayerEnt): List<ConfigTalent> {
        return playerTalents.getOrDefault(player.id, emptyList()).toList()
    }

    fun clearAllTalents(player: PlayerEnt) {
        playerTalents.remove(player.id)
    }

    private fun getTalentConfig(talentId: Int): ConfigTalent? {
        return try {
            org.forfun.mmorpg.game.base.GameContext.getDataManager().queryById(ConfigTalent::class.java, talentId)
        } catch (e: Exception) {
            null
        }
    }
}