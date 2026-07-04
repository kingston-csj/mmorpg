package org.forfun.mmorpg.game.scene.model

import org.forfun.mmorpg.game.scene.actor.SceneActor

/**
 * aoi分屏算法视野观察者
 */
class AoiObserver {

    /** 当前可见视野元素 */
    val actors = HashMap<Long, SceneActor>()
}
