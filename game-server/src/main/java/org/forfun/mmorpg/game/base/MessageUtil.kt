package org.forfun.mmorpg.game.base

import jforgame.socket.share.IdSession
import jforgame.socket.share.message.Message

object MessageUtil {

    @JvmStatic
    fun pushMessage(playerId: Long, message: Message) {
        val session = GameContext.getSessionManager()?.getSessionBy(playerId)
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
        if (session == null || message == null) {
            return
        }
        session.send(message)
    }

    @JvmStatic
    fun pushMessage(session: IdSession?, message: ByteArray) {
        if (session == null || message == null) {
            return
        }
        session.send(message)
    }
}
