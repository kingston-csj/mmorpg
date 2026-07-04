package org.forfun.mmorpg.game.scene.service

import jforgame.commons.util.TimeUtil
import jforgame.threadmodel.ThreadModel
import jforgame.threadmodel.dispatch.BaseDispatchTask
import jforgame.threadmodel.dispatch.DispatchThreadModel
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.base.SchedulerManager
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.model.Scene
import org.forfun.mmorpg.game.scene.model.WorldMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
open class SceneService {

    private val allMaps = ConcurrentHashMap<Int, WorldMap>()


    @Autowired
    lateinit var schedulerManager: SchedulerManager

    @Autowired
    lateinit var  threadModel: ThreadModel

    fun init() {
        // 普通常驻场景
        initNormalScenes()
        schedulerManager.scheduleAtFixedRate(this::tick, 1* TimeUtil.MILLIS_PER_SECOND, 300)
    }

    /**
     * 初始化所有普通场景
     */
    fun initNormalScenes() {
        GameContext.getDataManager().queryAll(ConfigMap::class.java)
            .filter { it.mapType.toInt() == 0 }
            .forEach { initNormalScenes(it) }
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

    private fun tick() {
        allMaps.forEach { (_, map) ->
            map.getScenes().forEach { scene ->
                val task = object : BaseDispatchTask() {
                    override fun action() {
                        scene.tick()
                    }
                }.apply {
                    dispatchKey = scene.getId()
                }
                threadModel.accept(task)
            }
        }
    }
}
