package com.kingston.mmorpg.game.player.facade;

import org.springframework.stereotype.Controller;

import com.kingston.mmorpg.framework.eventbus.EventBus;
import com.kingston.mmorpg.game.gm.GmCommands;
import com.kingston.mmorpg.game.gm.GmHandler;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import com.kingston.mmorpg.game.scene.actor.Player;

@Controller
public class PlayerFacade {
	
	
	@GmHandler(cmd = GmCommands.LEVEL)
	public void gmSetLevel(Player player, int level) {
		System.err.println("[gm]修改玩家等级为" + level);
		EventBus.getInstance().post(new PlayerLevelUpEvent(player));
	}
	
}
