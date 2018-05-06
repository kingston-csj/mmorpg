package com.kingston.mmorpg.game.player.service;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.game.player.message.ResPlayerLogin;
import com.kingston.mmorpg.game.scene.actor.Player;

@Component
public class PlayerService {
	
	public static final short CMD_REQ_LOGIN = 1;
	
	public static final short CMD_RES_LOGIN = 201;
	
	public ResPlayerLogin login(IoSession session, long playerId) {
		Player player = new Player();
		session.bindDipatcher(player);
		return new ResPlayerLogin();
	}
	

}
