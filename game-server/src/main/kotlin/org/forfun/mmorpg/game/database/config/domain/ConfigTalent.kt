package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.DataTable
import jforgame.data.annotation.Id
import org.forfun.mmorpg.game.skill.service.TriggerPoint

@DataTable(name = "configtalent")
class ConfigTalent {

    @Id
    var id: Int = 0

    /**
     * 天赋id
     */
    var talentId: Int = 0

    var level: Int = 1

    var preTalentId: Int = 0

    var costPoint: Int = 1

    var point1: TriggerPoint? = null
    var effectType1: String = ""
    var effectParams1: String = ""

    var point2: TriggerPoint? = null
    var effectType2: String = ""
    var effectParams2: String = ""

    var point3: TriggerPoint? = null
    var effectType3: String = ""
    var effectParams3: String = ""

    fun getEffectConfigs(): List<TalentEffectConfig> {
        val configs = mutableListOf<TalentEffectConfig>()
        if (point1 != null && effectType1.isNotEmpty()) {
            configs.add(TalentEffectConfig(point1!!, effectType1, effectParams1))
        }
        if (point2 != null && effectType2.isNotEmpty()) {
            configs.add(TalentEffectConfig(point2!!, effectType2, effectParams2))
        }
        if (point3 != null && effectType3.isNotEmpty()) {
            configs.add(TalentEffectConfig(point3!!, effectType3, effectParams3))
        }
        return configs
    }

    data class TalentEffectConfig(
        val triggerPoint: TriggerPoint,
        val effectType: String,
        val effectParams: String
    )
}