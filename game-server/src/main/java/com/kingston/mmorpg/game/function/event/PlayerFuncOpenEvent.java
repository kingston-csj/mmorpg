package com.kingston.mmorpg.game.function.event;

import com.kingston.mmorpg.framework.eventbus.BaseEvent;
import com.kingston.mmorpg.game.scene.actor.Player;

public class PlayerFuncOpenEvent  implements BaseEvent {

	private Player player;

	public PlayerFuncOpenEvent(Player player) {
		this.player = player;
	}

	@Override
	public Player getOwner() {
		return player;
	}

}
