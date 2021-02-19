package com.kingston.mmorpg.game.function.facade;

import com.kingston.mmorpg.framework.eventbus.Subscribe;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.function.model.FunctionOpenType;
import com.kingston.mmorpg.game.function.service.FunctionService;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FunctionFacade {
	
	@Autowired
	private FunctionService funcService;

	@Subscribe
	public void onPlayerLevelUp(PlayerLevelUpEvent levelUpEvent) {
		PlayerEnt player = levelUpEvent.getOwner();
		funcService.checkOpen(player, FunctionOpenType.LEVEL.getType());
	}
	
}
