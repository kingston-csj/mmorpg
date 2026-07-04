package org.forfun.mmorpg.game.cross.util

import jforgame.socket.share.IdSession
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.cross.service.RpcClientRouter
import org.forfun.mmorpg.game.logger.LoggerUtils
import java.util.concurrent.atomic.AtomicInteger

object CrossMessageUtil {

    private val idFactory = AtomicInteger(1)

    fun requestToCenter(message: Message) {
        val clientRouter = GameContext.getBean(RpcClientRouter::class.java)
        val session = clientRouter.centerSession
        if (session == null) {
            LoggerUtils.error("中心服链路不可达")
            return
        }
        session.send(message)
    }
}
