package org.forfun.mmorpg.game.skill.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.fight.model.BattleContext
import org.forfun.mmorpg.game.fight.service.SkillRangeSelector
import org.forfun.mmorpg.game.fight.service.SkillRelationSelector
import org.springframework.stereotype.Service

@Service
open class SkillService {

    companion object {
        const val CMD_RESP_SKILLS = 50
    }

    fun useSkill(player: PlayerEnt, skillId: Int) {
        val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, skillId)
        val rangeSelector = SkillRangeSelector.getByType(skillData.selector)
        val battleContext = BattleContext(skillId)

        val targets = player.aoiObserver.actors.values.toList()
            .run {
                // 第一步：区域筛选
                SkillRangeSelector.getByType(skillData.selector).select(battleContext, this)
            }
            .run {
                // 第二步：敌我关系筛选
                SkillRelationSelector.getByType(skillData.enemyRelation).select(battleContext, this)
            }

    }

}
