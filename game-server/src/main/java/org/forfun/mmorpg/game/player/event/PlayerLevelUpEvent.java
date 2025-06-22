package org.forfun.mmorpg.game.player.event;

import jforgame.commons.eventbus.BaseEvent;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

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