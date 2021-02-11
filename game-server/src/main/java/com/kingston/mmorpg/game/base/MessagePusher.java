package com.kingston.mmorpg.game.base;

import java.util.Collection;

import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.framework.net.socket.message.Message;

public class MessagePusher {

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
		session.sendPacket(message);
	}

}
