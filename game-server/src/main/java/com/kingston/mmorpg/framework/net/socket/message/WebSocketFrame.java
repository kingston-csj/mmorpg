package com.kingston.mmorpg.framework.net.socket.message;

import com.google.gson.Gson;

public class WebSocketFrame {
	
//	private short module;
//	
//	private short cmd;
	
	private String id;
	
	private String msg;
	
	public static WebSocketFrame valueOf(Message message) {
		WebSocketFrame frame = new WebSocketFrame();
		frame.id = message.getModule() + "_" +  message.getCmd();
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
