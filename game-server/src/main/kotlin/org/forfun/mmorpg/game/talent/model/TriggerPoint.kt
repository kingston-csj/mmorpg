package org.forfun.mmorpg.game.talent.model

enum class TriggerPoint {
    BeforeCasting,
    AfterTargetSelect,
    BeforeDamageCalc,
    AfterDamageCalc,
    OnCriticalHit,
    AfterCasting,
    OnDamaged,
    OnKill,
    OnHealed,
    OnDeath
}