package com.kingston.mmorpg.game.gm.facade;

import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.framework.net.socket.annotation.ModuleMeta;
import com.kingston.mmorpg.game.Modules;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.gm.GmDispatcher;
import com.kingston.mmorpg.game.gm.message.ReqGmCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ModuleMeta(module = Modules.GM)
public class GmFacade {

	@Autowired
	private GmDispatcher gmDispatcher;

	public void reqGmExec(IdSession session, ReqGmCommand req) {
		PlayerEnt player = session.getPlayer();
		String[] params = req.getParams().split("\\s+");
		gmDispatcher.dispatch(player, params);
	}

}
