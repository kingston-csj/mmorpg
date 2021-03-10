package org.forfun.mmorpg.game.ai.fsm;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.scene.actor.Monster;

public class Scene {

	private PlayerEnt player;

	private Monster monster;

	public PlayerEnt getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEnt player) {
		this.player = player;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

}
