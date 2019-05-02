package com.kingston.mmorpg.game.scene.director;

import com.kingston.mmorpg.game.scene.actor.ActorType;
import com.kingston.mmorpg.game.scene.actor.Monster;
import com.kingston.mmorpg.game.scene.actor.Player;
import com.kingston.mmorpg.game.scene.actor.SceneActor;

public class SceneDirector {

	public void onActorEnter(SceneActor actor) {
		if (actor.getType() == ActorType.Player) {
			onPlayerEnter((Player) actor);
		} else if (actor.getType() == ActorType.Monster) {
			onMonsterEnter((Monster) actor);
		}
	}
	
	public void onActorLeave(SceneActor actor) {
		if (actor.getType() == ActorType.Player) {
			onPlayerEnter((Player) actor);
		} else if (actor.getType() == ActorType.Monster) {
			onMonsterEnter((Monster) actor);
		}
	}

	protected void onPlayerEnter(Player player) {

	}

	protected void onMonsterEnter(Monster monster) {

	}
	
	protected void onPlayerLeave(Player player) {

	}

	protected void onMonsterLeave(Monster monster) {

	}

}
