package org.forfun.mmorpg.game.gm.facade;

import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.gm.GmDispatcher;
import org.forfun.mmorpg.game.gm.message.ReqGmCommand;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.annotation.ModuleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ModuleMeta(module = Modules.GM)
public class GmFacade {

	@Autowired
	private GmDispatcher gmDispatcher;

	public void reqGmExec(IdSession session, ReqGmCommand req) {
		PlayerEnt player = (PlayerEnt) session.getAttribute("PLAYER");
		String[] params = req.getParams().split("\\s+");
		gmDispatcher.dispatch(player, params);
	}

}
