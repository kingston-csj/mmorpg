package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.model.Scene
import org.forfun.mmorpg.game.scene.model.WorldMap
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
open class SceneService {

    private val allMaps = ConcurrentHashMap<Int, WorldMap>()

    /**
     * 初始化所有普通场景
     */
    fun initNormalScenes() {
        GameContext.dataManager().queryAll(ConfigMap::class.java)
            ?.filter { it.mapType.toInt() == 0 }
            ?.forEach { initNormalScenes(it) }
    }

    private fun initNormalScenes(configMap: ConfigMap) {
        val worldMap = WorldMap()
        allMaps[configMap.id] = worldMap
        for (i in 0 until configMap.coreLine) {
            val scene = Scene(worldMap)
            scene.init()
            worldMap.addScene(scene.getId(), scene)
        }
    }
}
