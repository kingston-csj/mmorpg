package org.forfun.mmorpg.game.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
