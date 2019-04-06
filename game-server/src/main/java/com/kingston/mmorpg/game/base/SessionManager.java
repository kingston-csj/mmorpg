package com.kingston.mmorpg.game.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.ChannelUtils;
import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.SessionCloseReason;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.scene.actor.Player;

import io.netty.channel.Channel;

@Component
public class SessionManager {

	private Logger logger = LoggerFactory.getLogger(SessionManager.class);

	/** 缓存通信上下文环境对应的登录用户（主要用于服务） */
	private Map<IoSession, Long> session2Players = new ConcurrentHashMap<>();

	/** 缓存用户id与对应的会话 */
	private ConcurrentMap<Long, IoSession> player2Sessions = new ConcurrentHashMap<>();

	/**
	 * 向单一在线用户发送数据包
	 */
	public void sendPacketTo(IoSession session, Message pact) {
		if (pact == null || session == null)
			return;
		session.sendPacket(pact);
	}

	public void sendPacketTo(Player player, Message pact) {
		IoSession session = player2Sessions.get(player.getId());
		if (session != null) {
			session.sendPacket(pact);
		}
	}

	public void sendPacketTo(Long playerId, Message pact) {
		if (pact == null || playerId <= 0)
			return;

		IoSession session = player2Sessions.get(playerId);
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

	public IoSession getSessionBy(long playerId) {
		return this.player2Sessions.get(playerId);
	}

	public long getPlayerIdBy(IoSession session) {
//		return this.session2Players.get(session);
		return 0;
	}

	public boolean registerSession(Player player, IoSession session) {
		session.setPlayer(player);
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
		IoSession session = ChannelUtils.getSessionBy(context);
		Long playerId = session2Players.remove(session);
		if (playerId != null) {
			player2Sessions.remove(playerId);
		}
		if (session != null) {
			session.close(SessionCloseReason.OVER_TIME);
		}
	}

}
