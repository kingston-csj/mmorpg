package org.forfun.mmorpg.game.skill.service

import org.forfun.mmorpg.framework.exception.BusinessRequestException
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.attribute.AttributeType
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.skill.model.DamageType
import org.forfun.mmorpg.game.skill.model.SkillType
import org.forfun.mmorpg.game.system.I18nId
import org.forfun.mmorpg.game.talent.model.TriggerPoint
import org.forfun.mmorpg.game.talent.service.TalentService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
open class SkillService {

    companion object {
        const val CMD_RESP_SKILLS = 50
    }

    private val talentService = GameContext.getBean(TalentService::class.java)

    fun useSkill(player: PlayerEnt, skillId: Int) {
        val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, skillId)
            ?: return

        if (!player.skillBox.hasSkill(skillId)) {
            throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)
        }

        val battleContext = BattleContext(skillId)
        battleContext.attacker = player

        talentService.trigger(TriggerPoint.BeforeCasting, battleContext)

        var targets = player.aoiObserver.actors.values.filterIsInstance<Creature>().toList()
        targets = SkillRangeSelector.getByType(skillData.selector).select(battleContext, targets)
        targets = SkillRelationSelector.getByType(skillData.enemyRelation).select(battleContext, targets)

        talentService.trigger(TriggerPoint.AfterTargetSelect, battleContext)

        if (skillData.aoeRange > 0 && targets.size > skillData.aoeRange) {
            targets = targets.subList(0, skillData.aoeRange)
        }

        battleContext.targets.addAll(targets)

        val skillType = SkillType.valueOf(skillData.skillType)
        when (skillType) {
            SkillType.DAMAGE -> handleDamageSkill(player, skillData, targets, battleContext)
            SkillType.HEAL -> handleHealSkill(player, skillData, targets, battleContext)
            SkillType.BUFF, SkillType.DEBUFF, SkillType.SPECIAL -> handleBuffSkill(player, skillData, targets, battleContext)
        }

        applySelfBuff(player, skillData)
        talentService.trigger(TriggerPoint.AfterCasting, battleContext)
    }

    private fun handleDamageSkill(attacker: Creature, skillData: ConfigSkill, targets: List<Creature>, context: BattleContext) {
        val damageType = DamageType.valueOf(skillData.damageType)
        talentService.trigger(TriggerPoint.BeforeDamageCalc, context)

        for (target in targets) {

            val finalDamage = calculateDamage(attacker, target, skillData, damageType)
            val isCritical = isCriticalHit(attacker, target)

            if (isCritical) {
                val criticalMultiplier = attacker.attrContainer.getAttrValue(AttributeType.CriticalDamage).toDouble() / 10000.0
                val criticalDamage = (finalDamage * criticalMultiplier).toInt()
                target.life.currHp -= criticalDamage
                talentService.trigger(TriggerPoint.OnCriticalHit, context)
            } else {
                target.life.currHp -= finalDamage
            }

            talentService.trigger(TriggerPoint.AfterDamageCalc, context)

            if (target.life.currHp <= 0) {
                target.life.currHp = 0
                talentService.trigger(TriggerPoint.OnKill, context)
            }

            applyTargetBuff(attacker, target, skillData)
        }
    }

    private fun calculateDamage(attacker: Creature, defender: Creature, skillData: ConfigSkill, damageType: DamageType): Int {
        val attackAttr = when (damageType) {
            DamageType.PHYSICAL -> AttributeType.PhysicalAttack
            DamageType.MAGIC -> AttributeType.MagicAttack
            DamageType.TRUE -> AttributeType.Attack
        }

        val attackValue = attacker.attrContainer.getAttrValue(attackAttr).toInt()
        val baseDamage = (attackValue * skillData.damageCoeff.toDouble() / 10000.0).toInt() + skillData.fixedDamage

        val defenseAttr = when (damageType) {
            DamageType.PHYSICAL -> AttributeType.PhysicalDefense
            DamageType.MAGIC -> AttributeType.MagicDefense
            DamageType.TRUE -> null
        }

        var finalDamage = baseDamage
        if (defenseAttr != null) {
            val defenseValue = defender.attrContainer.getAttrValue(defenseAttr).toInt()
            val reduceRatio = defenseValue.toDouble() / (defenseValue + 1000.0)
            finalDamage = (baseDamage * (1 - reduceRatio)).toInt()
        }

        return Math.max(finalDamage, 1)
    }

    private fun isCriticalHit(attacker: Creature, defender: Creature): Boolean {
        val criticalRate = attacker.attrContainer.getAttrValue(AttributeType.CriticalRate).toDouble() / 10000.0
        val criticalResist = defender.attrContainer.getAttrValue(AttributeType.CriticalResist).toDouble() / 10000.0
        val finalRate = Math.max(0.0, criticalRate - criticalResist)
        return Random.nextDouble() < finalRate
    }

    private fun handleHealSkill(healer: Creature, skillData: ConfigSkill, targets: List<Creature>, context: BattleContext) {
        for (target in targets) {

            val attackValue = healer.attrContainer.getAttrValue(AttributeType.MagicAttack).toInt()
            val baseHeal = (attackValue * skillData.healCoeff.toDouble() / 10000.0).toInt() + skillData.fixedHeal

            target.life.currHp = Math.min(target.life.currHp, target.life.maxHp)
            talentService.trigger(TriggerPoint.OnHealed, context)

            applyTargetBuff(healer, target, skillData)
        }
    }

    private fun handleBuffSkill(caster: Creature, skillData: ConfigSkill, targets: List<Creature>, context: BattleContext) {
        for (target in targets) {
            applyTargetBuff(caster, target, skillData)
        }
    }

    private fun applySelfBuff(caster: Creature, skillData: ConfigSkill) {
        if (skillData.selfBuffIds.isNotEmpty()) {
            val buffIds = skillData.selfBuffIds.split(",").map { it.toInt() }
            val buffService = GameContext.getBuffService()
            for (buffId in buffIds) {
                buffService.addBuff(caster, buffId, caster)
            }
        }
    }

    private fun applyTargetBuff(caster: Creature, target: Creature, skillData: ConfigSkill) {
        if (skillData.targetBuffIds.isNotEmpty()) {
            val buffIds = skillData.targetBuffIds.split(",").map { it.toInt() }
            val buffService = GameContext.getBuffService()
            for (buffId in buffIds) {
                buffService.addBuff(target, buffId, caster)
            }
        }
    }
}


