package com.kingston.mmorpg.game.scene.factory;

import com.kingston.mmorpg.game.scene.actor.SceneActor;

public interface ActorCreator {

	SceneActor create(ActorPrototype prototype, Object... params);

}
