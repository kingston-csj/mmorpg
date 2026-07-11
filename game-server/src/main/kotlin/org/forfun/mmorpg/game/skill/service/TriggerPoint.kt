package org.forfun.mmorpg.game.skill.service

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