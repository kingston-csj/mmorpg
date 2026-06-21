package org.forfun.mmorpg.game.util

import org.forfun.mmorpg.game.base.GameContext
import java.util.concurrent.atomic.AtomicLong

/**
 * 全局id生成器
 */
object IdFactory {

    private val generator = AtomicLong(0)

    /**
     * 生成全局唯一id
     */
    fun getNextId(): Long {
        //----------------id格式 -------------------------
        //----------long类型8个字节64个比特位----------------
        // 高16位          	| 中32位          |  低16位
        // serverId        系统秒数          自增长号

        val serverId = GameContext.serverConfig().serverId.toLong()
        return (serverId.shl(48)).or((((System.currentTimeMillis() / 1000) and 0xFFFFFFFF) shl 16))
            .or((generator.getAndIncrement() and 0xFFFF))
    }
}
