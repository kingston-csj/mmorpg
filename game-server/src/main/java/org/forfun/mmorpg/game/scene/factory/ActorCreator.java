package org.forfun.mmorpg.game.scene.factory;

import org.forfun.mmorpg.game.scene.actor.SceneActor;

public interface ActorCreator {

	SceneActor create(ActorPrototype prototype, Object... params);

}
