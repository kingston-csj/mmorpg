package org.forfun.mmorpg.game.function.facade;

import org.forfun.mmorpg.framework.eventbus.Subscribe;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.function.model.FunctionOpenType;
import org.forfun.mmorpg.game.function.service.FunctionService;
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent;
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
