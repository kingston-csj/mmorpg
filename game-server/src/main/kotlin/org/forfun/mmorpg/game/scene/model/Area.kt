package org.forfun.mmorpg.game.scene.model

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.scene.actor.SceneActor
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * 基于网格的AOI算法 将场景划分为等大的小区域
 */
class Area(val id: Int) {

    /** 区域宽度（单位为像素） */
    companion object {
        const val WIDTH = 300
        /** 区域高度（单位为像素） */
        const val HEIGHT = 300
    }

    private val actors = ConcurrentHashMap<Long, SceneActor>()

    private val playerSum = AtomicInteger(0)

    fun add(actor: SceneActor) {
        if (this.actors.put(actor.getId(), actor) == null) {
            if (actor is PlayerEnt) {
                playerSum.incrementAndGet()
            }
        }
    }

    fun remove(actor: SceneActor) {
        if (this.actors.remove(actor.getId()) != null) {
            if (actor is PlayerEnt) {
                playerSum.decrementAndGet()
            }
        }
    }

    fun getActors(): Collection<SceneActor> {
        return this.actors.values
    }
}
