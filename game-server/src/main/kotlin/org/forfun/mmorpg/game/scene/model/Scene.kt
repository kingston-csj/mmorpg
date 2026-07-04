package org.forfun.mmorpg.game.scene.model

import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.director.SceneDirector
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class Scene(private val parent: WorldMap) {

    companion object {
        private val idFactory = AtomicLong()

        const val AOI_RANGE = 300.0
    }

    /**
     * 场景分线号
     */
    var lineId: Int = 0

    /** 场景分屏 */
    private val areas = ConcurrentHashMap<Int, Area>()

    /** 当前场景的动态阻挡 */
    private val dynamicBlocks = HashSet<Int>()

    private var sceneDirector: SceneDirector? = null

    private var widthRange = 0
    private var heightRange = 0

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
    }

    fun openDynamicBlocks(blockId: Int) {
        this.dynamicBlocks.add(blockId)
    }

    fun closeDynamicBlocks(blockId: Int) {
        this.dynamicBlocks.remove(blockId)
    }

    private fun posToAreaIndex(x: Double, y: Double): Int {
        val col = (x / Area.WIDTH).toInt()
        val row = (y / Area.HEIGHT).toInt()
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
                    updateAoi(actor)
                }
            }
        }
    }

    /**
     * 更新每个对象的aoi可视范围
     * 十字链表算法
     */
    private fun updateAoi(observer: Creature) {
        val col = (observer.posX / Area.WIDTH).toInt()
        val row = (observer.posY / Area.HEIGHT).toInt()

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

        observer.aoiObserver.actors.clear()

        val observerIndex = allActors.indexOfFirst { it.getId() == observer.getId() }

        if (observerIndex >= 0) {
            for (i in observerIndex - 1 downTo 0) {
                val other = allActors[i]
                val distance = Math.abs(other.posX - observer.posX)
                if (distance <= AOI_RANGE) {
                    observer.aoiObserver.actors[other.getId()] = other
                } else {
                    break
                }
            }

            for (i in observerIndex + 1 until allActors.size) {
                val other = allActors[i]
                val distance = Math.abs(other.posX - observer.posX)
                if (distance <= AOI_RANGE) {
                    observer.aoiObserver.actors[other.getId()] = other
                } else {
                    break
                }
            }
        }
    }

    fun actorEnter(actor: SceneActor) {
        val index = posToAreaIndex(actor.posX, actor.posY)
        areas[index]?.add(actor)
        sceneDirector!!.onActorEnter(actor)
    }

    fun actorLeave(actor: SceneActor) {
        val index = posToAreaIndex(actor.posX, actor.posY)
        areas[index]?.remove(actor)
        sceneDirector!!.onActorLeave(actor)
    }

    fun getId(): Long {
        return 0
    }
}