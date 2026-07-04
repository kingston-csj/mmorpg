package org.forfun.mmorpg.game.scene.director

/**
 * 副本默认场景管理器
 */
abstract class BaseDungeonDirector : SceneDirector() {

    protected val historyPlayerIds = HashSet<Long>()

    protected val alivePlayerIds = HashSet<Long>()

    abstract fun init()

    abstract fun prepare()

    abstract fun start()

    abstract fun tryFinish()

    fun end() {
        settlement()
    }

    abstract fun settlement()
}
