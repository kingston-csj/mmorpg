package org.forfun.mmorpg.game.player.facade;

import jforgame.socket.share.IdSession;
import jforgame.socket.share.annotation.MessageRoute;
import jforgame.socket.share.annotation.RequestHandler;
import org.forfun.mmorpg.framework.eventbus.EventBus;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.exception.BusinessRequestException;
import org.forfun.mmorpg.game.gm.GmCommands;
import org.forfun.mmorpg.game.gm.GmHandler;
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent;
import org.forfun.mmorpg.game.player.event.PlayerLoginEvent;
import org.forfun.mmorpg.game.player.message.ReqAccountLogin;
import org.forfun.mmorpg.game.player.message.ReqPlayerLogin;
import org.forfun.mmorpg.game.player.message.ReqSelectPlayer;
import org.forfun.mmorpg.game.player.message.ResPlayerLogin;
import org.forfun.mmorpg.game.player.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@MessageRoute(module = Modules.PLAYER)
public class PlayerFacade {
	
	
	@Autowired
	private LoginService loginService;

	@RequestHandler
	public void reqAccountLogin(IdSession session, ReqAccountLogin request) {
		if (request.getAccountId() > 0) {
			throw new BusinessRequestException(100);
		}
		loginService.handleAccountLogin(session, request.getAccountId(), request.getPassword());
	}

	@RequestHandler
	public void reqSelectPlayer(IdSession session, ReqSelectPlayer request) {
		loginService.handleSelectPlayer(session, request.getPlayerId());
	}

	@RequestHandler
	public void reqPlayerLogin(IdSession session, ReqPlayerLogin request) {
		long playerId = request.getPlayerId();
		System.out.println("角色[" + playerId + "]登录");

		session.send(new ResPlayerLogin());

		PlayerEnt player = new PlayerEnt();
		player.setId(playerId);
		EventBus.getInstance().post(new PlayerLoginEvent(player));
	}

	@GmHandler(cmd = GmCommands.LEVEL)
	public void gmSetLevel(PlayerEnt player, int level) {
		System.err.println("[gm]修改玩家等级为" + level);
		player.setLevel(level);
		GameContext.getPlayerService().savePlayer(player);
		EventBus.getInstance().post(new PlayerLevelUpEvent(player));
	}

}
