package org.forfun.mmorpg.game.scene.model

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *  世界地图模型
 */
class WorldMap {

    /** 以该地图为模型的所有场景 */
    private val scenes = ConcurrentHashMap<Int, Scene>()

    /** 地图模型id */
    var modelId: Int = 0

    /** 读写锁 */
    private val lock = ReentrantReadWriteLock()

    fun addScene(id: Int, scene: Scene) {
        scenes[id] = scene
    }

    fun getScenes(): Collection<Scene> {
        return scenes.values
    }

    fun getScene(sceneId: Int) :Scene? {
        return scenes[sceneId]
    }
}
