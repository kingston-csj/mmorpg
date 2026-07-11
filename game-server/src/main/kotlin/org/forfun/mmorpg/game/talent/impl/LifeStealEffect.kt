package org.forfun.mmorpg.game.talent.impl

import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.talent.TalentEffect
import org.forfun.mmorpg.game.scene.actor.Creature

class LifeStealEffect : TalentEffect {

    override fun execute(context: BattleContext, talent: ConfigTalent, effectConfig: ConfigTalent.TalentEffectConfig) {
        val attacker = context.attacker
        if (attacker !is Creature) return

        val params = effectConfig.effectParams.split(",")
        if (params.isEmpty()) return

        val ratio = params[0].toDouble() / 10000.0
        for (target in context.targets) {
            if (target is Creature) {
                val damageDealt = (target.life.maxHp - target.life.currHp).toDouble()
                val healAmount = (damageDealt * ratio).toLong()
                attacker.life.currHp = Math.min(attacker.life.currHp + healAmount, attacker.life.maxHp)
            }
        }
    }
}