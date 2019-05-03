package com.kingston.mmorpg.game.function.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingston.mmorpg.framework.eventbus.EventBus;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.config.container.ConfigFunctionContainer;
import com.kingston.mmorpg.game.database.config.domain.ConfigFunction;
import com.kingston.mmorpg.game.function.event.PlayerFuncOpenEvent;
import com.kingston.mmorpg.game.function.model.FunctionOpenType;
import com.kingston.mmorpg.game.scene.actor.Player;

/**
 * 功能开启与关闭
 * 
 * @author kingston
 *
 */
@Service
public class FunctionService {
	
	@Autowired
	private ConfigFunctionContainer configFuncContainer;
	
	public void checkOpen(Player player, int type) {
		Collection<ConfigFunction> targets = configFuncContainer.queryFunctionsBy(FunctionOpenType.LEVEL.getType());
		targets.stream().filter(func -> !player.isOpenedFunction(func.getId()))
		.forEach(func -> {
			if (player.getLevel() >= func.getOpenMainParam()) {
				levelOpenFunc(player, func.getId());
			}
		});
	}
	
	private void levelOpenFunc(Player player, int funcId) {
		player.openFunction(funcId);
		SpringContext.getPlayerService().savePlayer(player);
		EventBus.getInstance().post(new PlayerFuncOpenEvent(player));
	}

}
