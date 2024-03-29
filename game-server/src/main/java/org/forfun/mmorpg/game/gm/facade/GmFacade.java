package org.forfun.mmorpg.game.gm.facade;

import jforgame.socket.share.IdSession;
import jforgame.socket.share.annotation.MessageRoute;
import jforgame.socket.share.annotation.RequestHandler;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.gm.GmDispatcher;
import org.forfun.mmorpg.game.gm.message.ReqGmCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@MessageRoute(module = Modules.GM)
public class GmFacade {

	@Autowired
	private GmDispatcher gmDispatcher;

	@RequestHandler
	public void reqGmExec(IdSession session, ReqGmCommand req) {
		PlayerEnt player = (PlayerEnt) session.getAttribute("PLAYER");
		String[] params = req.getParams().split("\\s+");
		gmDispatcher.dispatch(player, params);
	}

}
