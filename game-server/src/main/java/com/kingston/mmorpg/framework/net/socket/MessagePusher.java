package com.kingston.mmorpg.framework.net.socket;

import java.util.Collection;

import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.base.SpringContext;

public class MessagePusher {

	public static void pushMessage(long playerId, Message message) {
		IdSession session = SpringContext.getSessionManager().getSessionBy(playerId);
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
