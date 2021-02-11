package com.kingston.mmorpg.game.player.facade;

import com.kingston.mmorpg.framework.eventbus.EventBus;
import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.framework.net.socket.annotation.ModuleMeta;
import com.kingston.mmorpg.game.Modules;
import com.kingston.mmorpg.game.base.GameContext;
import com.kingston.mmorpg.game.gm.GmCommands;
import com.kingston.mmorpg.game.gm.GmHandler;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import com.kingston.mmorpg.game.player.event.PlayerLoginEvent;
import com.kingston.mmorpg.game.player.message.ReqAccountLogin;
import com.kingston.mmorpg.game.player.message.ReqPlayerLogin;
import com.kingston.mmorpg.game.player.message.ReqSelectPlayer;
import com.kingston.mmorpg.game.player.message.ResPlayerLogin;
import com.kingston.mmorpg.game.player.service.LoginService;
import com.kingston.mmorpg.game.scene.actor.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ModuleMeta(module = Modules.PLAYER)
public class PlayerFacade {
	
	
	@Autowired
	private LoginService loginService;

	public void reqAccountLogin(IdSession session, ReqAccountLogin request) {
		loginService.handleAccountLogin(session, request.getAccountId(), request.getPassword());
	}

	public void reqSelectPlayer(IdSession session, ReqSelectPlayer requst) {
		loginService.handleSelectPlayer(session, requst.getPlayerId());
	}

	public void reqPlayerLogin(IdSession session, ReqPlayerLogin request) {
		long playerId = request.getPlayerId();
		System.out.println("角色[" + playerId + "]登录");

		session.sendPacket(new ResPlayerLogin());

		Player player = new Player();
		player.setId(playerId);
		session.setPlayer(player);

		EventBus.getInstance().post(new PlayerLoginEvent(player));
	}

	@GmHandler(cmd = GmCommands.LEVEL)
	public void gmSetLevel(Player player, int level) {
		System.err.println("[gm]修改玩家等级为" + level);
		player.setLevel(level);
		GameContext.getPlayerService().savePlayer(player);
		EventBus.getInstance().post(new PlayerLevelUpEvent(player));
	}

}
