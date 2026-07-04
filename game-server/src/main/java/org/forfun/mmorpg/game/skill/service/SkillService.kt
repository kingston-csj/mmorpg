package org.forfun.mmorpg.game.skill.service

import jakarta.persistence.Id
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.fight.model.BattleContext
import org.springframework.stereotype.Service

@Service
open class SkillService {

    companion object {
        const val CMD_RESP_SKILLS = 50
    }

    fun useSkill(player: PlayerEnt, skillId: Int) {
        val skillData = GameContext.getDataManager().queryById(ConfigSkill::class.java, skillId)
        val battleContext = BattleContext(skillId)

    }
}
