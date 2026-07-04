package org.forfun.mmorpg.game.scene.factory

import org.forfun.mmorpg.game.scene.actor.SceneActor

interface ActorCreator {

    fun create(prototype: ActorPrototype, vararg params: Any): SceneActor
}
