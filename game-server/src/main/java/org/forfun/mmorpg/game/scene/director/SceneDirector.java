package org.forfun.mmorpg.game.scene.director;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.scene.actor.ActorType;
import org.forfun.mmorpg.game.scene.actor.Monster;
import org.forfun.mmorpg.game.scene.actor.SceneActor;

public class SceneDirector {

	public void onActorEnter(SceneActor actor) {
		if (actor.getType() == ActorType.Player) {
			onPlayerEnter((PlayerEnt) actor);
		} else if (actor.getType() == ActorType.Monster) {
			onMonsterEnter((Monster) actor);
		}
	}
	
	public void onActorLeave(SceneActor actor) {
		if (actor.getType() == ActorType.Player) {
			onPlayerEnter((PlayerEnt) actor);
		} else if (actor.getType() == ActorType.Monster) {
			onMonsterEnter((Monster) actor);
		}
	}

	protected void onPlayerEnter(PlayerEnt player) {

	}

	protected void onMonsterEnter(Monster monster) {

	}
	
	protected void onPlayerLeave(PlayerEnt player) {

	}

	protected void onMonsterLeave(Monster monster) {

	}

}
