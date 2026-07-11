package org.forfun.mmorpg.game.talent.service

import org.forfun.mmorpg.framework.exception.BusinessRequestException
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigTalent
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.skill.model.BattleContext
import org.forfun.mmorpg.game.system.I18nId
import org.forfun.mmorpg.game.talent.model.TriggerPoint
import org.springframework.stereotype.Service

@Service
class TalentService {

    /**
     * 触发指定触发点的天赋效果
     * 直接调用玩家 TalentBox 的缓存，避免重复查询
     */
    fun trigger(triggerPoint: TriggerPoint, context: BattleContext) {
        val attacker = context.attacker
        if (attacker is PlayerEnt) {
            attacker.talentBox.trigger(triggerPoint, context)
        }
    }

    /**
     * 学习天赋
     * 学习时将天赋效果缓存到 TalentBox 中
     */
    fun learnTalent(player: PlayerEnt, talentId: Int) {
        if (player.talentBox.hasTalent(talentId)) {
            throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)
        }
        val talentCfg = GameContext.getDataManager().queryById(ConfigTalent::class.java, talentId)
            ?: throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)

        player.talentBox.talentLevels[talentCfg.talentId] = 1
        player.talentBox.addTalent(talentCfg)

        GameContext.getPlayerService().savePlayer(player)
    }

    /**
     * 遗忘天赋
     */
    fun forgetTalent(player: PlayerEnt, talentId: Int) {
        if (!player.talentBox.hasTalent(talentId)) {
            throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)
        }
        player.talentBox.talentLevels.remove(talentId)
        player.talentBox.removeTalent(talentId)

        GameContext.getPlayerService().savePlayer(player)
    }

    /**
     * 升级天赋
     */
    fun upgradeTalent(player: PlayerEnt, talentId: Int) {
        if (!player.talentBox.hasTalent(talentId)) {
            throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)
        }
        val talentCfg = GameContext.getDataManager().queryById(ConfigTalent::class.java, talentId)
            ?: throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)

        val currentLevel = player.talentBox.talentLevels[talentId] ?: 0
        if (currentLevel >= talentCfg.level) {
            throw BusinessRequestException(I18nId.ILLEGAL_ARGUMENT)
        }

        player.talentBox.talentLevels[talentId] = currentLevel + 1
        player.talentBox.removeTalent(talentId)
        player.talentBox.addTalent(talentCfg)

        GameContext.getPlayerService().savePlayer(player)
    }

    /**
     * 初始化玩家天赋效果缓存（玩家登录时调用）
     */
    fun initTalentEffects(player: PlayerEnt) {
        player.talentBox.clearEffects()
        for ((talentId, _) in player.talentBox.talentLevels) {
            val talentCfg = GameContext.getDataManager().queryById(ConfigTalent::class.java, talentId)
            if (talentCfg != null) {
                player.talentBox.addTalent(talentCfg)
            }
        }
    }
}