package org.forfun.mmorpg.game.talent

object TalentEffectRegistry {

    private val effects = mutableMapOf<String, TalentEffect>()

    fun register(effectType: String, effect: TalentEffect) {
        effects[effectType] = effect
    }

    fun getEffect(effectType: String): TalentEffect? {
        return effects[effectType]
    }

    fun unregister(effectType: String) {
        effects.remove(effectType)
    }

    fun getAllEffects(): Map<String, TalentEffect> {
        return effects.toMap()
    }
}