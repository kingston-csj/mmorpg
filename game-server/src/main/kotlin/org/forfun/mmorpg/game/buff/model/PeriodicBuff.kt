package org.forfun.mmorpg.game.buff.model

import org.forfun.mmorpg.game.base.GameContext
import java.util.concurrent.Future

/**
 * 周期性buff
 */
abstract class PeriodicBuff : Buff() {

    /** 每一个tick执行的间隔 */
    private var periodicInterval: Int = 0

    private var periodicTask: Future<*>? = null

    override fun init() {
        try {
            super.init()
        } finally {
            registerFrameTask()
        }
    }

    private fun registerFrameTask() {
        cancleFrameTask()
        periodicTask = GameContext.schedulerManager().schedule({
            enterFrame()
        }, periodicInterval.toLong())
    }

    private fun cancleFrameTask() {
        if (periodicTask != null) {
            periodicTask!!.cancel(false)
            periodicTask = null
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
        } finally {
            cancleFrameTask()
        }
    }

    abstract fun enterFrame()
}
