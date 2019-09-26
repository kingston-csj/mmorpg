package com.kingston.mmorpg.game.player.message;

import java.util.ArrayList;
import java.util.List;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.message.vo.PlayerLoginVo;
import com.kingston.mmorpg.game.player.service.PlayerService;

@MessageMeta(cmd = PlayerService.CMD_RES_ACCOUNT_LOGIN)
public class ResAccountLogin extends Message {

	private List<PlayerLoginVo> players = new ArrayList<>();

	public List<PlayerLoginVo> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerLoginVo> players) {
		this.players = players;
	}

}
