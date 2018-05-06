package com.kingston.mmorpg.game.player.facade;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.annotation.RequestMapping;
import com.kingston.mmorpg.game.player.message.ReqPlayerLogin;

@Component
public class PlayerFacade {
	
	@RequestMapping
	public void reqPlayerLogin(IoSession session, ReqPlayerLogin request) {
		long playerId = request.getPlayerId();
		System.out.println("角色[" + playerId + "]登录");
	}

}
