package org.forfun.mmorpg.game.skill.talent

import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.skill.model.BattleContext

interface TalentEffect {

    fun execute(context: BattleContext, talent: ConfigTalent, effectConfig: ConfigTalent.TalentEffectConfig)
}