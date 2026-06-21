package org.forfun.mmorpg.game.scene.model

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *  世界地图模型
 */
class WorldMap {

    /** 以该地图为模型的所有场景 */
    private val scenes = ConcurrentHashMap<Long, Scene>()

    /** 地图模型id */
    var modelId: Int = 0

    /** 读写锁 */
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()

    fun addScene(id: Long, scene: Scene) {
        scenes[id] = scene
    }
}
