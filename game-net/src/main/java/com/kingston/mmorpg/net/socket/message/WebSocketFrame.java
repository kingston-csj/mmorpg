package com.kingston.mmorpg.net.socket.message;

import com.google.gson.Gson;
import com.kingston.mmorpg.net.socket.MessageFactory;

public class WebSocketFrame {

	private String id;

	private String msg;

	public static WebSocketFrame valueOf(Message message) {
		WebSocketFrame frame = new WebSocketFrame();
		frame.id = "" + MessageFactory.getInstance().getMessageId(message.getClass());
		frame.msg = new Gson().toJson(message);
		return frame;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
