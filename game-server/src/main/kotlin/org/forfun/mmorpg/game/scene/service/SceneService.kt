package org.forfun.mmorpg.game.scene.service

import jforgame.commons.util.TimeUtil
import jforgame.threadmodel.ThreadModel
import jforgame.threadmodel.dispatch.BaseDispatchTask
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.base.SchedulerManager
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.scene.model.Point
import org.forfun.mmorpg.game.scene.model.Scene
import org.forfun.mmorpg.game.scene.model.WorldMap
import org.forfun.mmorpg.game.scene.service.PathValidator
import org.forfun.mmorpg.game.util.ThreadSafaUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
open class SceneService {

    private val allMaps = ConcurrentHashMap<Int, WorldMap>()


    @Autowired
    lateinit var schedulerManager: SchedulerManager

    @Autowired
    lateinit var threadModel: ThreadModel

    fun init() {
        // 普通常驻场景
        initNormalScenes()
        schedulerManager.scheduleAtFixedRate(this::tick, 1 * TimeUtil.MILLIS_PER_SECOND, 300)
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
            scene.spawnManager.init()
            worldMap.addScene(scene.getId(), scene)
        }
    }

    /**
     * 创建副本场景
     */
    fun createDungeon(mapId: Int): Scene {
        val configMap = GameContext.getDataManager().queryById(ConfigMap::class.java, mapId)
            ?: throw IllegalArgumentException("mapId: $mapId is not exist")

        val factory = SceneCreateFactory.entries.firstOrNull { it.type == configMap.mapType.toInt() }
            ?: throw IllegalArgumentException("mapType: ${configMap.mapType} is not supported")

        val worldMap = allMaps.computeIfAbsent(mapId) { WorldMap() }

        synchronized(worldMap) {
            val director = factory.director.getDeclaredConstructor().newInstance()
            val scene = Scene(worldMap, director)
            scene.lineId = worldMap.generateLineId()
            scene.init()
            worldMap.addScene(scene.getId(), scene)
            return scene
        }
    }


    private fun tick() {
        allMaps.forEach { (mapId, map) ->
            map.getScenes().forEach { scene ->
                val task = object : BaseDispatchTask() {
                    override fun action() {
                        scene.tick()
                    }
                }.apply {
                    dispatchKey = (mapId * 100 + scene.getId()).toLong()
                }
                threadModel.accept(task)
            }
        }
    }

    /**
     * 进入场景
     */
    fun enterScene(player: PlayerEnt, mapId: Int, lineId: Int) {
        val world = allMaps[mapId] ?: throw IllegalArgumentException("map is null")
        val scene = world.getScene(lineId) ?: throw IllegalArgumentException("mapId : $mapId lineId: $lineId is null")
        ThreadSafaUtil.addTask(scene.dispatchKey(), Runnable {
            val bornPoint = scene.getRandomBornPoint()
            player.posX = bornPoint.x
            player.posY = bornPoint.y
            player.mapId = mapId
            player.lineId = lineId
            player.scene = scene
            scene.actorEnter(player)
        })
    }


    /**
     * 玩家移动
     */
    fun move(player: PlayerEnt, roads: List<Point>) {
        if (roads.isEmpty()) {
            return
        }
        // 这里不用切线程
        val scene = player.scene
        if (PathValidator.isValidPath(scene, player.posX.toDouble(), player.posY.toDouble(), roads)) {
            player.startMove(roads)
        }
    }

}
