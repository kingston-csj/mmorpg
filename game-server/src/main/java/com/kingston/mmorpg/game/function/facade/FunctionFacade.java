package com.kingston.mmorpg.game.function.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kingston.mmorpg.framework.eventbus.Subscribe;
import com.kingston.mmorpg.game.function.model.FunctionOpenType;
import com.kingston.mmorpg.game.function.service.FunctionService;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import com.kingston.mmorpg.game.scene.actor.Player;

@Controller
public class FunctionFacade {
	
	@Autowired
	private FunctionService funcService;

	@Subscribe
	public void onPlayerLevelUp(PlayerLevelUpEvent levelUpEvent) {
		Player player = levelUpEvent.getOwner();
		funcService.checkOpen(player, FunctionOpenType.LEVEL.getType());
	}
	
}
