package com.kingston.mmorpg.game.gm.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMapping;
import com.kingston.mmorpg.game.gm.GmDispatcher;
import com.kingston.mmorpg.game.gm.message.ReqGmCommand;
import com.kingston.mmorpg.game.scene.actor.Player;

@Controller
public class GmFacade {

	@Autowired
	private GmDispatcher gmDispatcher;

	@MessageMapping
	public void reqGmExec(IoSession session, ReqGmCommand req) {
		Player player = session.getPlayer();
		String[] params = req.getParams().split("\\s+");
		gmDispatcher.dispatch(player, params);
	}

}
