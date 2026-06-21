package org.forfun.mmorpg.game.player.service

import jforgame.socket.share.IdSession
import org.forfun.mmorpg.game.account.AccountService
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.base.MessageUtil
import org.forfun.mmorpg.game.player.message.ResAccountLogin
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class LoginService {

    @Autowired
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var playerService: PlayerService

    /**
     * @param accountId 账号流水号
     * @param password  账号密码
     */
    fun handleAccountLogin(session: IdSession?, accountId: Long, password: String?) {
        var account = accountService.getEntity(accountId)

        val players = ArrayList<PlayerLoginVo>()
        val accountProfile = playerService.getAccountProfiles(accountId)
        val playerProfiles = accountProfile?.players

        playerProfiles?.forEach { playerProfile ->
            val vo = PlayerLoginVo()
            vo.id = playerProfile.playerId
            vo.name = playerProfile.name
            players.add(vo)
        }

        val loginMessage = ResAccountLogin()
        loginMessage.players = players
        MessageUtil.pushMessage(session, loginMessage)
    }

    /**
     * 选角登录
     */
    fun handleSelectPlayer(session: IdSession?, playerId: Long) {
        var player = playerService.getPlayer(playerId)
        player = playerService.getPlayer(playerId)
        // 绑定session与玩家id
        session?.setAttribute("playerId", playerId)
        // 加入在线列表
        GameContext.getSessionManager()?.registerNewPlayer(player, session)
    }
}
