package org.forfun.mmorpg.game.player.event;

import jforgame.commons.eventbus.BaseEvent;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

public class PlayerLoginEvent implements BaseEvent {

	private PlayerEnt player;

	public PlayerLoginEvent(PlayerEnt player) {
		this.player = player;
	}

	@Override
	public PlayerEnt getOwner() {
		return player;
	}

}
