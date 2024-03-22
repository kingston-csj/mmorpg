package org.forfun.mmorpg.game.base;


import jforgame.socket.share.IdSession;
import jforgame.socket.share.message.Message;

import java.util.Collection;


public class MessageUtil {

	public static void pushMessage(long playerId, Message message) {
		IdSession session = GameContext.getSessionManager().getSessionBy(playerId);
		pushMessage(session, message);
	}

	public static void pushMessage(Collection<Long> playerIds, Message message) {
		for (long playerId : playerIds) {
			pushMessage(playerId, message);
		}
	}

	public static void pushMessage(IdSession session, Message message) {
		if (session == null || message == null) {
			return;
		}
		session.send(message);
	}


	public static void pushMessage(IdSession session, byte[] message) {
		if (session == null || message == null) {
			return;
		}
		session.send(message);
	}

}
