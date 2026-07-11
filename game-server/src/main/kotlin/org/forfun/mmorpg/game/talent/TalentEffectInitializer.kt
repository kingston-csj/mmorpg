package org.forfun.mmorpg.game.talent

import org.forfun.mmorpg.game.talent.impl.IncreaseCriticalRateEffect
import org.forfun.mmorpg.game.talent.impl.IncreaseDamageEffect
import org.forfun.mmorpg.game.talent.impl.IncreaseAttackEffect
import org.forfun.mmorpg.game.talent.impl.LifeStealEffect
import org.forfun.mmorpg.game.talent.impl.OnKillHealEffect
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