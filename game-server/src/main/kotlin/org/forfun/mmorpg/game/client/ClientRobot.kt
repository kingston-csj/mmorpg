package org.forfun.mmorpg.game.client

import jforgame.socket.share.IdSession
import org.forfun.mmorpg.game.gm.message.ReqGmCommand
import org.forfun.mmorpg.game.player.message.ReqAccountLogin
import org.forfun.mmorpg.game.player.message.ReqCreateNewPlayer
import org.forfun.mmorpg.game.player.message.ReqPlayerLogin

class ClientRobot(var session: IdSession) {
    fun sendGm() {
        val req = ReqGmCommand()
        req.params = "level 99"
        session.send(req)
    }

    fun createNew() {
        val req = ReqCreateNewPlayer()
        req.name = "Happy"
        session.send(req)
    }

    fun accountLogin() {
        val req = ReqAccountLogin()
        req.password = "forfun"
        req.accountId = 123L
        session.send(req)
    }

    fun playerLogin(playerId: Long) {
        val req = ReqPlayerLogin()
        req.playerId = playerId
        session.send(req)
    }
}