package com.kingston.mmorpg.game.player.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.Modules;
import com.kingston.mmorpg.game.player.service.PlayerService;

@MessageMeta(module = Modules.PLAYER, cmd = PlayerService.CMD_REQ_LOGIN)
public class ReqPlayerLogin extends Message {
	
	private long playerId;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "ReqPlayerLogin [playerId=" + playerId + "]";
	}
}
