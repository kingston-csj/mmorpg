package com.kingston.mmorpg.game.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kingston.mmorpg.framework.eventbus.EventBus;
import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.annotation.RequestMapping;
import com.kingston.mmorpg.game.login.message.ReqAccontLogin;
import com.kingston.mmorpg.game.login.message.ReqPlayerLogin;
import com.kingston.mmorpg.game.login.message.ResPlayerLogin;
import com.kingston.mmorpg.game.login.service.LoginService;
import com.kingston.mmorpg.game.player.event.PlayerLoginEvent;
import com.kingston.mmorpg.game.player.message.ReqSelectPlayer;
import com.kingston.mmorpg.game.scene.actor.Player;

@Controller
public class LoginFacade {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping
	public void reqAccountLogin(IoSession session, ReqAccontLogin request) {
		loginService.handleAccountLogin(session, request.getAccountId(), request.getPassword());
	}

	@RequestMapping
	public void reqSelectPlayer(IoSession session, ReqSelectPlayer requst) {
		loginService.handleSelectPlayer(session, requst.getPlayerId());
	}

	
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
	

}
