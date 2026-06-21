package org.forfun.mmorpg.game.skill.facade

import jforgame.commons.eventbus.Subscribe
import jforgame.socket.share.annotation.MessageRoute
import org.forfun.mmorpg.game.Modules
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.base.MessageUtil
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent
import org.forfun.mmorpg.game.player.event.PlayerLoginEvent
import org.forfun.mmorpg.game.skill.message.RespPlayerSkills
import org.springframework.stereotype.Controller

@Controller
@MessageRoute(module = Modules.SKILL)
open class SkillFacade {

    @Subscribe
    fun onPlayerLogin(loginEvent: PlayerLoginEvent) {
        val player = loginEvent.owner
    }

    @Subscribe
    fun onPlayerLevelUp(upEvent: PlayerLevelUpEvent) {
        val player = upEvent.owner
        val nowLevel = 10

        val skills = GameContext.getDataManager()?.queryAll(ConfigSkill::class.java)
        skills?.filter { it.needLevel < nowLevel }?.forEach { configSkill ->
            System.err.println("玩家升级，学会新技能->" + configSkill.name)
        }

        System.err.println("角色" + player.id + "登录，准备下发技能列表")
        val resp = RespPlayerSkills()
        resp.skills = listOf(1)
        MessageUtil.pushMessage(player.id, resp)
    }
}
