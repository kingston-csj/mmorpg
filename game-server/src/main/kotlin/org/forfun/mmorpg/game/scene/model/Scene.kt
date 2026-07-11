package org.forfun.mmorpg.game.scene.model

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.director.SceneDirector
import org.forfun.mmorpg.game.scene.service.MapBlockLoader
import org.forfun.mmorpg.game.scene.service.SceneSpawnManager
import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs

class Scene(private val parent: WorldMap, director: SceneDirector? = null) {

    companion object {
        const val AOI_RANGE = 300.0
    }

    var mapId: Int = parent.modelId

    /**
     * 场景分线号
     */
    var lineId: Int = 0

    /** 场景分屏 */
    private val areas = ConcurrentHashMap<Int, Area>()

    /** 当前场景的动态阻挡ID集合 */
    private val dynamicBlocks = HashSet<Int>()

    /** 静态地图阻挡数据 */
    private lateinit var staticBlockData: MapBlockData

    /** 动态阻挡数据缓存 */
    private val dynamicBlockCache = mutableMapOf<Int, MapBlockData>()

    /** 当前合并后的阻挡矩阵 */
    private lateinit var currentBlockData: MapBlockData

    lateinit var sceneDirector: SceneDirector

    init {
        if (director != null) {
            director.scene = this
            this.sceneDirector = director
        } else {
            this.sceneDirector = SceneDirector(this)
        }
    }

    /** 当前回合数（副本场景使用） */
    var currentRound: Int = 0

    val spawnManager = SceneSpawnManager(this)

    private var widthRange = 0
    private var heightRange = 0

    var actors: MutableMap<Long, SceneActor> = HashMap()

    fun init() {
        val configMap = ConfigMap()
        val width = configMap.width
        val height = configMap.height

        widthRange = if (width % Area.WIDTH == 0) width / Area.WIDTH else width / Area.WIDTH + 1
        heightRange = if (height % Area.HEIGHT == 0) height / Area.HEIGHT else height / Area.HEIGHT + 1

        for (i in 0 until widthRange) {
            for (j in 0 until heightRange) {
                val index = i * 1000 + j
                val area = Area(index)
                areas[index] = area
            }
        }

        loadMapBlocks()
    }

    private fun loadMapBlocks() {
        staticBlockData = MapBlockLoader.loadStaticMap(mapId)
        currentBlockData = staticBlockData.copy()
    }

    fun openDynamicBlocks(blockId: Int) {
        if (dynamicBlocks.add(blockId)) {
            val dynamicData = dynamicBlockCache.computeIfAbsent(blockId) {
                MapBlockLoader.loadDynamicMap(mapId, blockId)
            }
            currentBlockData.merge(dynamicData)
        }
    }

    fun closeDynamicBlocks(blockId: Int) {
        if (dynamicBlocks.remove(blockId)) {
            val dynamicData = dynamicBlockCache[blockId]
            if (dynamicData != null) {
                rebuildBlockData()
            }
        }
    }

    private fun rebuildBlockData() {
        currentBlockData = staticBlockData.copy()
        for (blockId in dynamicBlocks) {
            val dynamicData = dynamicBlockCache[blockId]
            if (dynamicData != null) {
                currentBlockData.merge(dynamicData)
            }
        }
    }

    fun isBlocked(x: Int, y: Int): Boolean {
        return currentBlockData.isBlock(x, y)
    }

    private fun posToAreaIndex(x: Short, y: Short): Int {
        val col = (x.toDouble() / Area.WIDTH).toInt()
        val row = (y.toDouble() / Area.HEIGHT).toInt()
        return col * 1000 + row
    }

    /**
     * 返回当前格子周围的九宫格列表
     */
    private fun getNineAreas(col: Int, row: Int): List<Area> {
        val result = mutableListOf<Area>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                val nc = col + dx
                val nr = row + dy
                if (nc in 0..<widthRange && nr >= 0 && nr < heightRange) {
                    val area = areas[nc * 1000 + nr]
                    area?.let { result.add(it) }
                }
            }
        }
        return result
    }

    /**
     * 地图心跳
     */
    fun tick() {
        for (area in areas.values) {
            for (actor in area.getActors()) {
                if (actor is Creature) {
                    if (actor.isMoving) {
                        updatePosition(actor)
                    }
                    updateAoi(actor)
                }
            }
        }
    }

    private fun updatePosition(creature: Creature) {
        val waypoints = creature.waypoints ?: return
        val speed = creature.moveSpeed
        val distancePerTick = speed * 0.3

        while (creature.currentWaypointIndex < waypoints.size) {
            val targetPoint = waypoints[creature.currentWaypointIndex]
            val dx = targetPoint.x.toDouble() - creature.posX.toDouble()
            val dy = targetPoint.y.toDouble() - creature.posY.toDouble()
            val distanceToTarget = kotlin.math.sqrt(dx * dx + dy * dy)

            if (distanceToTarget <= distancePerTick) {
                creature.posX = targetPoint.x
                creature.posY = targetPoint.y
                creature.currentWaypointIndex++

                if (creature.currentWaypointIndex >= waypoints.size) {
                    creature.stopMove()
                }
            } else {
                val ratio = distancePerTick / distanceToTarget
                creature.posX = (creature.posX + dx * ratio).toInt().toShort()
                creature.posY = (creature.posY + dy * ratio).toInt().toShort()
                break
            }
        }
    }

    fun getAreas(): Collection<Area> {
        return areas.values
    }

    fun getAliveMonsters(): List<SceneActor> {
        return actors.values.filter { it.type == ActorType.Monster }
    }

    /**
     * 更新每个对象的aoi可视范围
     * 十字链表算法
     */
    private fun updateAoi(observer: Creature) {
        val col = (observer.posX.toDouble() / Area.WIDTH).toInt()
        val row = (observer.posY.toDouble() / Area.HEIGHT).toInt()

        val nineAreas = getNineAreas(col, row)
        val allActors = mutableListOf<Creature>()

        for (area in nineAreas) {
            for (actor in area.getActors()) {
                if (actor is Creature && actor !== observer) {
                    allActors.add(actor)
                }
            }
        }

        allActors.sortBy { it.posX }

        val previousActorsMap = HashMap(observer.aoiObserver.actors)
        val currentActors = mutableListOf<Creature>()

        val observerIndex = allActors.indexOfFirst { it.getId() == observer.getId() }

        if (observerIndex >= 0) {
            for (i in observerIndex - 1 downTo 0) {
                val other = allActors[i]
                val distance = abs(other.posX.toDouble() - observer.posX.toDouble())
                if (distance <= AOI_RANGE) {
                    currentActors.add(other)
                } else {
                    break
                }
            }

            for (i in observerIndex + 1 until allActors.size) {
                val other = allActors[i]
                val distance = abs(other.posX.toDouble() - observer.posX.toDouble())
                if (distance <= AOI_RANGE) {
                    currentActors.add(other)
                } else {
                    break
                }
            }
        }

        observer.aoiObserver.actors.clear()
        for (actor in currentActors) {
            observer.aoiObserver.actors[actor.getId()] = actor
        }

        for (actor in currentActors) {
            if (!previousActorsMap.containsKey(actor.getId())) {
                observer.aoiObserver.onSee(actor)
            }
        }

        for ((prevId, disappeared) in previousActorsMap) {
            if (!observer.aoiObserver.actors.containsKey(prevId)) {
                observer.aoiObserver.onDisappear(disappeared)
            }
        }
    }

    fun actorEnter(actor: SceneActor) {
        val index = posToAreaIndex(actor.posX.toShort(), actor.posY.toShort())
        areas[index]?.add(actor)
        sceneDirector.onActorEnter(actor)
    }

    fun actorLeave(actor: SceneActor) {
        val index = posToAreaIndex(actor.posX.toShort(), actor.posY.toShort())
        areas[index]?.remove(actor)
        sceneDirector.onActorLeave(actor)
    }

    fun getId(): Int {
        return lineId
    }

    fun dispatchKey(): Long {
        return (mapId * 100 + lineId).toLong()
    }

    fun advanceRound() {
        currentRound++
        spawnManager.spawnRoundMonsters(currentRound)
    }

    fun spawnRoundMonsters(round: Int) {
        spawnManager.spawnRoundMonsters(round)
    }

    fun getRandomBornPoint(): Point {
        val configMap = GameContext.getDataManager().queryById(ConfigMap::class.java, mapId)
            ?: return Point(0, 0)

        val centerX = configMap.bornX
        val centerY = configMap.bornY
        val range = 5

        val candidates = mutableListOf<Point>()
        for (dx in -range..range) {
            for (dy in -range..range) {
                val x = centerX + dx
                val y = centerY + dy
                if (!isBlocked(x, y)) {
                    candidates.add(Point(x.toShort(), y.toShort()))
                }
            }
        }

        if (candidates.isEmpty()) {
            return Point(centerX.toShort(), centerY.toShort())
        }

        return candidates[Random().nextInt(candidates.size)]
    }
}