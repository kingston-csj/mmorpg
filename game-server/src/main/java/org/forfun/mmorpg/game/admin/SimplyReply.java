package org.forfun.mmorpg.game.admin;

public class SimplyReply {

	private int status;

	private String msg;

	public static SimplyReply valueOfSucc(String msg) {
		SimplyReply reply = new SimplyReply();
		reply.msg = msg;
		return reply;
	}

	public static SimplyReply valueOfFailed(int code, String msg) {
		SimplyReply reply = new SimplyReply();
		reply.status = code;
		reply.msg = msg;
		return reply;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
