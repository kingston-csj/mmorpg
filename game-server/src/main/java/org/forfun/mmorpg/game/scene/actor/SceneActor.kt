package org.forfun.mmorpg.game.scene.actor

import org.forfun.mmorpg.game.ai.fsm.Scene

/**
 * 场景里的各种演员
 */
abstract class SceneActor : GameObject {

    protected var mapId: Int = 0

    protected var lineId: Int = 0

    var scene: Scene? = null

    abstract val type: ActorType

}
