package com.kingston.mmorpg.game.ai.fsm;

import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;

public class Scene {

	private Player player;

	private Monster monster;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

}
