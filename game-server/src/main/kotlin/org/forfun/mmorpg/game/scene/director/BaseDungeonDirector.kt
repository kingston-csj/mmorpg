package org.forfun.mmorpg.game.scene.director

import org.forfun.mmorpg.game.scene.actor.Monster

/**
 * 副本默认场景管理器
 */
abstract class BaseDungeonDirector : SceneDirector() {

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

    abstract fun init()

    abstract fun prepare()

    abstract fun start()

    abstract fun tryFinish()

    fun end() {
        settlement()
    }

    abstract fun settlement()
}
