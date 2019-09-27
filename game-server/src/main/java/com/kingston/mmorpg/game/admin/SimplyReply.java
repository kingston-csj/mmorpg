package com.kingston.mmorpg.game.admin;

public class SimplyReply {

	private String status;

	private String msg;

	public SimplyReply() {
	}

	public SimplyReply(String status, String msg) {

		this.status = status;
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
