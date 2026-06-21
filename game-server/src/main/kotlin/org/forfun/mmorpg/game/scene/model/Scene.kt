package org.forfun.mmorpg.game.scene.model

import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.director.SceneDirector
import java.util.concurrent.atomic.AtomicLong

class Scene(private val parent: WorldMap) {

    companion object {
        private val idFactory = AtomicLong()
    }

    /**
     * 场景分线号
     */
    var lineId: Int = 0

    /** 场景分屏 */
    private val areas = HashMap<Int, Area>()

    /** 当前场景的动态阻挡 */
    private val dynamicBlocks = HashSet<Int>()

    private var sceneDirector: SceneDirector? = null


    fun init() {
        val configMap = ConfigMap()
        // 根据地图长宽进行分屏
        val width = configMap.width
        val height = configMap.height

        val widthRange = if (width % Area.WIDTH == 0) width / Area.WIDTH else width / Area.WIDTH + 1
        val heightRange = if (height % Area.HEIGHT == 0) height / Area.HEIGHT else height / Area.HEIGHT + 1
        // TODO 改为延迟初始化
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

    fun tick() {}

    fun actorEnter(actor: SceneActor) {
        sceneDirector!!.onActorEnter(actor)
    }

    fun actorLeave(actor: SceneActor) {
        sceneDirector!!.onActorLeave(actor)
    }

    fun getId(): Long {
        return 0
    }
}
