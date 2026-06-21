package org.forfun.mmorpg.game

import jforgame.socket.server.ServerNode
import org.forfun.mmorpg.game.base.GameContext
import org.springframework.stereotype.Component

@Component
open class BaseServer {

    @Throws(Exception::class)
    fun start() {
        initFramework()

        Runtime.getRuntime().addShutdownHook(Thread { stop() })
    }

    @Throws(Exception::class)
    private fun initFramework() {
        // NIO socket（多端口）启动
        GameContext.getBean(ServerNode::class.java).start()
    }

    companion object {
        private fun stop() {
            // 实现停止逻辑
        }
    }
}
