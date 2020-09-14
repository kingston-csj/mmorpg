package com.kingston.mmorpg.framework.net.socket.netty;

import java.util.HashMap;
import java.util.Map;

import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.framework.net.socket.SessionCloseReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.message.WebSocketFrame;
import com.kingston.mmorpg.framework.net.task.IDispatch;
import com.kingston.mmorpg.game.scene.actor.Player;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 链接的会话
 * 
 * @author kingston
 */
public class NettySession implements IdSession {

	private static final Logger logger = LoggerFactory.getLogger(NettySession.class);

	/** 网络连接channel */
	private Channel channel;

	private Player player;

	/** ip地址 */
	private String ipAddr;

	private IDispatch dispatcher;

	/** 拓展用，保存一些个人数据 */
	private Map<String, Object> attrs = new HashMap<>();

	private ChannelType channelType;

	public NettySession() {

	}

	public NettySession(Channel channel, ChannelType channelType) {
		this.channel = channel;
		this.ipAddr = ChannelUtils.getIp(channel);
		this.dispatcher = anonymousDispatcher;
		this.channelType = channelType;
	}

	/**
	 * 向客户端发送消息
	 * 
	 * @param packet
	 */
	public void sendPacket(Message packet) {
		if (packet == null) {
			return;
		}
		if (channelType == ChannelType.SOCKET) {
			channel.writeAndFlush(packet);
		} else {
			WebSocketFrame frame = WebSocketFrame.valueOf(packet);
			channel.writeAndFlush(new TextWebSocketFrame(new Gson().toJson(frame)));
		}
	}

	@Override
	public long getOwnerId() {
		return player.getId();
	}

	@Override
	public Object setAttribute(String key, Object value) {
		return this.attrs.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public IDispatch getDispatcher() {
		return dispatcher;
	}

	public void bindDispatcher(IDispatch dispatcher) {
		this.dispatcher = dispatcher;
	}

	public boolean isClose() {
		if (channel == null) {
			return true;
		}
		return !channel.isActive() || !channel.isOpen();
	}

	/**
	 * 关闭session
	 * 
	 * @param reason {@link SessionCloseReason}
	 */
	public void close(SessionCloseReason reason) {
		try {
			if (this.channel == null) {
				return;
			}
			if (channel.isOpen()) {
				channel.close();
				logger.info("close session[{}], reason is {}", getOwnerId(), reason);
			} else {
				logger.info("session[{}] already close, reason is {}", getOwnerId(), reason);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 匿名分发器，用于角色未登录
	 */
	static IDispatch anonymousDispatcher = new IDispatch() {

		@Override
		public int dispatchKey() {
			return 0;
		}

	};

}
