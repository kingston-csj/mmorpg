package org.forfun.mmorpg.game

import jforgame.socket.share.IdSession
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.cross.message.Rpc_G2C_FetchFightServerNodes
import org.forfun.mmorpg.game.cross.service.RpcClientRouter

class GameServerLayer : ServerLayer {

    override fun init() {
        // 实现游戏服初始化逻辑
    }

    override fun onCenterServerConnected() {
        val centerSession = GameContext.getBean(RpcClientRouter::class.java).centerSession
        centerSession?.send(Rpc_G2C_FetchFightServerNodes())
    }
}
