package com.kingston.mmorpg.game.player.facade;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.eventbus.EventBus;
import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.annotation.RequestMapping;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.gm.GmCommands;
import com.kingston.mmorpg.game.gm.GmHandler;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import com.kingston.mmorpg.game.player.event.PlayerLoginEvent;
import com.kingston.mmorpg.game.player.message.ReqPlayerLogin;
import com.kingston.mmorpg.game.player.message.ResPlayerLogin;
import com.kingston.mmorpg.game.scene.actor.Player;

@Component
public class PlayerFacade {
	
	@RequestMapping
	public void reqPlayerLogin(IoSession session, ReqPlayerLogin request) {
		long playerId = request.getPlayerId();
		System.out.println("角色[" + playerId + "]登录");
		
		session.sendPacket(new ResPlayerLogin());
		
		Player player = new Player();
		player.setId(playerId);
		session.setPlayer(player);
		
		EventBus.getInstance().post(new PlayerLoginEvent(player));
	}
	
	
	@GmHandler(cmd = GmCommands.LEVEL)
	public void gmSetLevel(Player player, String param) {
		int level = Integer.parseInt(param);
		System.err.println("[gm]修改玩家等级为" + level);
		EventBus.getInstance().post(new PlayerLevelUpEvent(player));
	}
	
}
