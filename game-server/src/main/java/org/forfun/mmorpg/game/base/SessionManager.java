package org.forfun.mmorpg.game.base;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.socket.netty.ChannelUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SessionManager {

	private Logger logger = LoggerFactory.getLogger(SessionManager.class);

	/** 缓存通信上下文环境对应的登录用户（主要用于服务） */
	private Map<IdSession, Long> session2Players = new ConcurrentHashMap<>();

	/** 缓存用户id与对应的会话 */
	private ConcurrentMap<Long, IdSession> player2Sessions = new ConcurrentHashMap<>();

	/**
	 * 向单一在线用户发送数据包
	 */
	public void sendPacketTo(IdSession session, Message pact) {
		if (pact == null || session == null)
			return;
		session.sendPacket(pact);
	}

	public void sendPacketTo(PlayerEnt player, Message pact) {
		IdSession session = player2Sessions.get(player.getId());
		if (session != null) {
			session.sendPacket(pact);
		}
	}

	public void sendPacketTo(Long playerId, Message pact) {
		if (pact == null || playerId <= 0)
			return;

		IdSession session = player2Sessions.get(playerId);
		if (session != null) {
			session.sendPacket(pact);
		}
	}

	/**
	 * 向所有在线用户发送数据包
	 */
	public void sendPacketToAllUsers(Message pact) {
		if (pact == null)
			return;

		player2Sessions.values().forEach((session) -> session.sendPacket(pact));
	}

	public IdSession getSessionBy(long playerId) {
		return this.player2Sessions.get(playerId);
	}

	public long getPlayerIdBy(IdSession session) {
//		return this.session2Players.get(session);
		return 0;
	}

	public boolean registerSession(PlayerEnt player, IdSession session) {
		session.setAttribute("PLAYER", player);
		player2Sessions.put(player.getId(), session);

		logger.info("[{}] registered...", player.getId());
		return true;
	}

	/**
	 * 注销用户通信渠道
	 */
	public void ungisterPlayerChannel(Channel context) {
		if (context == null) {
			return;
		}
		IdSession session = ChannelUtils.getSessionBy(context);
		Long playerId = session2Players.remove(session);
		if (playerId != null) {
			player2Sessions.remove(playerId);
		}
		if (session != null) {
//			session.close(SessionCloseReason.OVER_TIME);
		}
	}

}
