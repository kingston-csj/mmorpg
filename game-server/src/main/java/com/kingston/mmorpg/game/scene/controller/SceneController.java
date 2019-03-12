package com.kingston.mmorpg.game.scene.controller;

import com.kingston.mmorpg.game.scene.actor.ActorType;
import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;
import com.kingston.mmorpg.game.scene.actor.SceneActor;

public class SceneController {

	public void onActorEnter(SceneActor actor) {
		if (actor.getActorType() == ActorType.Player) {
			onPlayerEnter((Player) actor);
		} else if (actor.getActorType() == ActorType.Monster) {
			onMonsterEnter((Monster) actor);
		}
	}

	public void onPlayerEnter(Player player) {

	}

	public void onMonsterEnter(Monster monster) {

	}

}
