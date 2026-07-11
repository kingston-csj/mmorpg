package org.forfun.mmorpg.game.util

import jforgame.threadmodel.dispatch.BaseDispatchTask
import jforgame.threadmodel.dispatch.DispatchThreadModel
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

/**
 * 线程安全工具
 */
object ThreadSafaUtil {


    fun addTask(key: Long, task: Runnable): Unit {
        val wrapper = object : BaseDispatchTask() {
            override fun action() = task.run()
        }.apply { dispatchKey = key }
        GameContext.getBean(DispatchThreadModel::class.java).accept(wrapper)
    }

    fun addTask(player: PlayerEnt, task: Runnable): Unit = addTask(player.dispatchKey().toLong(), task)
}