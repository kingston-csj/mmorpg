package org.forfun.mmorpg.game.base

import jforgame.socket.share.IdSession
import jforgame.socket.share.message.Message

object MessageUtil {

    @JvmStatic
    fun pushMessage(playerId: Long, message: Message) {
        val session = GameContext.sessionManager().getSessionBy(playerId)
        pushMessage(session, message)
    }

    @JvmStatic
    fun pushMessage(playerIds: Collection<Long>, message: Message) {
        for (playerId in playerIds) {
            pushMessage(playerId, message)
        }
    }

    @JvmStatic
    fun pushMessage(session: IdSession?, message: Message) {
        session?.send(message)
    }

}
