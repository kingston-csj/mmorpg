package com.kingston.mmorpg.game.player.event;

import com.kingston.mmorpg.framework.eventbus.BaseEvent;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;

public class PlayerLevelUpEvent implements BaseEvent {

	private PlayerEnt player;

	public PlayerLevelUpEvent(PlayerEnt player) {
		this.player = player;
	}

	@Override
	public PlayerEnt getOwner() {
		return player;
	}

}