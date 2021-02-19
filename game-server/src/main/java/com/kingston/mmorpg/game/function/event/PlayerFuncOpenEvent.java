package com.kingston.mmorpg.game.function.event;

import com.kingston.mmorpg.framework.eventbus.BaseEvent;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;

public class PlayerFuncOpenEvent  implements BaseEvent {

	private PlayerEnt player;

	public PlayerFuncOpenEvent(PlayerEnt player) {
		this.player = player;
	}

	@Override
	public PlayerEnt getOwner() {
		return player;
	}

}
