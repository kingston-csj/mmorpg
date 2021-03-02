package com.kingston.mmorpg.client;

import com.kingston.mmorpg.net.message.Message;

import io.netty.channel.Channel;

public class IoSession {
	
	/** 网络连接channel */
	private Channel channel;

	private String ipAddr;

	private boolean reconnected;

	public IoSession() {

	}

	public IoSession(Channel channel) {
		this.channel = channel;
	}
	
	
	/**
	 * 向客户端发送消息
	 * @param message
	 */
	public void sendPacket(Message message) {
		if (message == null) {
			return;
		}
		if (channel != null) {
			channel.writeAndFlush(message);
		}
	}
	
}
