package com.kingston.mmorpg.framework.net.socket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.framework.net.socket.message.WebSocketFrame;
import com.kingston.mmorpg.framework.net.socket.task.IDispatch;
import com.kingston.mmorpg.game.scene.actor.Player;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 链接的会话
 * @author kingston
 */
public class IoSession {
	
	private static final Logger logger = LoggerFactory.getLogger(IoSession.class);

	/** 网络连接channel */
	private Channel channel;

	private Player player;

	/** ip地址 */
	private String ipAddr;

	private boolean reconnected;
	
	private IDispatch dipatcher;
	
	/** 拓展用，保存一些个人数据  */
	private Map<String, Object> attrs = new HashMap<>();
	
	private ChannelType channelType;

	public IoSession() {

	}

	public IoSession(Channel channel, ChannelType channelType) {
		this.channel = channel;
		this.ipAddr = ChannelUtils.getIp(channel);
		this.dipatcher = anoymousDispatcher;
		this.channelType = channelType;
	}
	
	/**
	 * 向客户端发送消息
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

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public boolean isReconnected() {
		return reconnected;
	}

	public void setReconnected(boolean reconnected) {
		this.reconnected = reconnected;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public IDispatch getDipatcher() {
		return dipatcher;
	}

	public void bindDipatcher(IDispatch dipatcher) {
		this.dipatcher = dipatcher;
	}

	public boolean isClose() {
		if (channel == null) {
			return true;
		}
		return !channel.isActive() ||
			   !channel.isOpen();
	}
	
	/**
	 * 关闭session 
	 * @param reason {@link SessionCloseReason}
	 */
	public void close(SessionCloseReason reason) {
		try{
			if (this.channel == null) {
				return;
			}
			if (channel.isOpen()) {
				channel.close();
				logger.info("close session[{}], reason is {}", getPlayer().getId(), reason);
			}else{
				logger.info("session[{}] already close, reason is {}", getPlayer().getId(), reason);
			}
		}catch(Exception e){
		}
	}
	
	/**
	 * 匿名分发器，用于角色未登录
	 */
	static IDispatch anoymousDispatcher = new IDispatch() {

		@Override
		public int dispatchMap() {
			return 0;
		}

		@Override
		public int dispatchLine() {
			return 0;
		}
		
	};

}
