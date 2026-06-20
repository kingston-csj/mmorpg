package org.forfun.mmorpg.game.player.facade

import jforgame.socket.share.IdSession
import jforgame.socket.share.annotation.MessageRoute
import jforgame.socket.share.annotation.RequestHandler
import org.forfun.mmorpg.game.Modules
import org.forfun.mmorpg.game.base.GameContext.Companion.getEventBus
import org.forfun.mmorpg.game.base.GameContext.Companion.getPlayerService
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.gm.GmCommands
import org.forfun.mmorpg.game.gm.GmHandler
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent
import org.forfun.mmorpg.game.player.event.PlayerLoginEvent
import org.forfun.mmorpg.game.player.message.ReqAccountLogin
import org.forfun.mmorpg.game.player.message.ReqPlayerLogin
import org.forfun.mmorpg.game.player.message.ReqSelectPlayer
import org.forfun.mmorpg.game.player.message.ResPlayerLogin
import org.forfun.mmorpg.game.player.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
@MessageRoute(module = Modules.PLAYER)
class PlayerFacade {
    @Autowired
    private val loginService: LoginService? = null

    @RequestHandler
    fun reqAccountLogin(session: IdSession?, request: ReqAccountLogin) {
        loginService!!.handleAccountLogin(session, request.accountId, request.password)
    }

    @RequestHandler
    fun reqSelectPlayer(session: IdSession?, request: ReqSelectPlayer) {
        loginService!!.handleSelectPlayer(session, request.getPlayerId())
    }

    @RequestHandler
    fun reqPlayerLogin(session: IdSession, request: ReqPlayerLogin) {
        val playerId = request.playerId
        println("角色[$playerId]登录")

        session.send(ResPlayerLogin())

        val player = PlayerEnt()
        player.setId(playerId)
        getEventBus()!!.post(PlayerLoginEvent(player))
    }

    @GmHandler(cmd = GmCommands.LEVEL)
    fun gmSetLevel(player: PlayerEnt, level: Int) {
        System.err.println("[gm]修改玩家等级为$level")
        player.level = level
        getPlayerService()!!.savePlayer(player)
        getEventBus()!!.post(PlayerLevelUpEvent(player))
    }
}