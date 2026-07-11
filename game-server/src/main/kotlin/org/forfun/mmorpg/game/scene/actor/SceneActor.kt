package org.forfun.mmorpg.game.scene.actor

import org.forfun.mmorpg.game.scene.model.Scene


/**
 * 场景里的各种演员
 */
abstract class SceneActor : GameObject {

    var mapId: Int = 0

    var lineId: Int = 0

    lateinit var scene: Scene

    abstract val type: ActorType

    var posX: Short = 0

    var posY: Short = 0

}
