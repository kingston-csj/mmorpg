package org.forfun.mmorpg.game.skill.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.fight.model.BattleContext
import org.forfun.mmorpg.game.fight.service.RangeSelectorFilter
import org.forfun.mmorpg.game.fight.service.SkillSelectorFilter
import org.springframework.stereotype.Service

@Service
open class SkillService {

    companion object {
        const val CMD_RESP_SKILLS = 50
    }

    fun useSkill(player: PlayerEnt, skillId: Int) {
        val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, skillId)
        val rangeSelector = RangeSelectorFilter.getByType(skillData.selector)
        val battleContext = BattleContext(skillId)
        // 根据几何区域挑选目标
        var targets = rangeSelector.select(battleContext, player.aoiObserver.actors.values.toList())
        // 根据敌对策略挑选目标
        targets = SkillSelectorFilter.getByType(skillData.enemyRelation).select(battleContext, targets)

    }
}
