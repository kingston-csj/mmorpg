package org.forfun.mmorpg.game.scene.director

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.base.SchedulerManager
import org.forfun.mmorpg.game.database.config.domain.ConfigMap
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.model.Scene
import org.forfun.mmorpg.game.util.ThreadSafaUtil

/**
 * 副本默认场景管理器
 */
abstract class BaseDungeonDirector(scene: Scene) : SceneDirector(scene) {

    protected val historyPlayerIds = HashSet<Long>()

    protected val alivePlayerIds = HashSet<Long>()


    override fun onMonsterDead(monster: Monster) {
        checkAndAdvanceRound(monster)
    }

    private fun checkAndAdvanceRound(monster: Monster) {
        val scene = monster.scene
        if (scene.getAliveMonsters().isEmpty()) {
            scene.advanceRound()
        }
    }

    fun init() {
        // 准备阶段，下发倒计时
        prepare()
    }

    fun prepare() {
        val mapCfg = GameContext.getDataManager().queryById(ConfigMap::class.java, scene.mapId)
        SchedulerManager.instance.schedule({
            // 切线程
            ThreadSafaUtil.addTask(scene.dispatchKey(), Runnable {
                this.start()
            })

        }, mapCfg.prepareSeconds.toLong())

    }

    fun start() {
        scene.spawnManager.init()
        val mapCfg = GameContext.getDataManager().queryById(ConfigMap::class.java, scene.mapId)
        SchedulerManager.instance.schedule({
            // 切线程
            ThreadSafaUtil.addTask(scene.dispatchKey(), Runnable {
                this.end()
            })

        }, mapCfg.lastSeconds.toLong())
    }

    fun tryFinish() {}

    fun end() {
        settlement()
    }

    abstract fun settlement()
}
