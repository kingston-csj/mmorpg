package org.forfun.mmorpg.game.skill.talent.impl

import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.skill.talent.TalentEffect
import org.forfun.mmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.attribute.AttributeType

class IncreaseAttackEffect : TalentEffect {

    override fun execute(context: BattleContext, talent: ConfigTalent, effectConfig: ConfigTalent.TalentEffectConfig) {
        val attacker = context.attacker
        if (attacker !is Creature) return

        val params = effectConfig.effectParams.split(",")
        if (params.isEmpty()) return

        val value = params[0].toInt()
        attacker.attrContainer.addAttr(AttributeType.PhysicalAttack, value.toLong())
    }
}