package com.kingston.mmorpg.game.gm.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.Modules;

@MessageMeta(module = Modules.GM, cmd = 1)
public class ReqGmCommand extends Message {

	private String params;

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ReqGmCommand [params=" + params + "]";
	}

}
