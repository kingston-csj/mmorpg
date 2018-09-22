package com.kingston.mmorpg.game.scene.factory;

import com.kingston.mmorpg.game.scene.actor.SceneActor;
import com.kingston.mmorpg.game.scene.model.ConfigActor;

public abstract class ActorCreator {
	
	public abstract SceneActor create(ConfigActor config);

}
