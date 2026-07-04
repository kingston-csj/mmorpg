package org.forfun.mmorpg.game.base

import jforgame.commons.util.NumberUtil
import jforgame.socket.share.IdSession
import org.apache.mina.core.session.AttributeKey
import org.apache.mina.core.session.IoSession
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.springframework.stereotype.Component
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Component
open class SessionManager {

    /** distributeKey auto generator  */
    private val distributeKeyGenerator = AtomicInteger()
    /** key=playerId, value=session */
    private val player2sessions = ConcurrentHashMap<Long, IdSession>()

    fun registerNewPlayer(player: PlayerEnt, session: IdSession?) {
        // biding playerId to session
        session?.setAttribute(IdSession.ID, player.id)
        session?.setAttribute("PLAYER", player)
        this.player2sessions[player.id] = session as IdSession
    }

    /**
     * get session's playerId
     * @param session
     * @return
     */
    fun getPlayerIdBy(session: IdSession?): Long {
        return if (session != null) {
            NumberUtil.longValue(session.id)
        } else {
            0
        }
    }

    fun getSessionBy(playerId: Long): IdSession? {
        return player2sessions[playerId]
    }

    /**
     * get appointed sessionAttr
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getSessionAttr(session: IoSession, attrKey: AttributeKey, attrType: Class<T>): T {
        return session.getAttribute(attrKey, attrType) as T
    }

    fun getNextSessionId(): Int {
        return this.distributeKeyGenerator.getAndIncrement()
    }

    fun getRemoteIp(session: IoSession): String {
        return (session.getRemoteAddress() as InetSocketAddress).address.hostAddress
    }
}
