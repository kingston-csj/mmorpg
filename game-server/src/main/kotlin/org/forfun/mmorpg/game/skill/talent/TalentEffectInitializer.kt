package org.forfun.mmorpg.game.skill.talent

import org.forfun.mmorpg.game.skill.talent.impl.IncreaseCriticalRateEffect
import org.forfun.mmorpg.game.skill.talent.impl.IncreaseDamageEffect
import org.forfun.mmorpg.game.skill.talent.impl.IncreaseAttackEffect
import org.forfun.mmorpg.game.skill.talent.impl.LifeStealEffect
import org.forfun.mmorpg.game.skill.talent.impl.OnKillHealEffect
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class TalentEffectInitializer : CommandLineRunner {

    override fun run(vararg args: String?) {
        TalentEffectRegistry.register("IncreaseCriticalRate", IncreaseCriticalRateEffect())
        TalentEffectRegistry.register("IncreaseDamage", IncreaseDamageEffect())
        TalentEffectRegistry.register("IncreaseAttack", IncreaseAttackEffect())
        TalentEffectRegistry.register("LifeSteal", LifeStealEffect())
        TalentEffectRegistry.register("OnKillHeal", OnKillHealEffect())
    }
}