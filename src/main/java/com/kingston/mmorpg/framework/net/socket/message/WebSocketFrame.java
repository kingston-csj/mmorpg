package com.kingston.mmorpg.framework.net.socket.message;

import com.google.gson.Gson;

public class WebSocketFrame {
	
	private short module;
	
	private short cmd;
	
	private String msg;
	
	public static WebSocketFrame valueOf(Message message) {
		WebSocketFrame frame = new WebSocketFrame();
		frame.module = message.getModule();
		frame.cmd = message.getCmd();
		frame.msg = new Gson().toJson(message);
		return frame;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
