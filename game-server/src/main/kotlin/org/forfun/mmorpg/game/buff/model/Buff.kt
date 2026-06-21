package org.forfun.mmorpg.game.buff.model

import io.micrometer.core.instrument.config.validate.PropertyValidator.getDuration
import org.forfun.mmmorpg.game.scene.actor.Creature
import org.forfun.mmorpg.game.base.GameContext
import java.util.concurrent.Future

abstract class Buff {

    var modelId: Int = 0

    /** 激活时间 */
    private var activeTime: Long = 0

    /** 持续时长 */
    var duration: Long = 0

    var overlapLayer: Int = 0

    var owner: Creature? = null

    var origin: Creature? = null

    private var timeOutTask: Future<*>? = null

    open fun init() {
        activeTime = System.currentTimeMillis()
        if (!isPermanent) {
            registerTimeOutTask()
        }
        onStart()
    }

    open fun onStart() {}

    fun registerTimeOutTask() {
        cancleTimeOutTask()
        timeOutTask = GameContext.schedulerManager().schedule({
            destroy()
        }, duration)
    }

    fun cancleTimeOutTask() {
        if (timeOutTask != null) {
            timeOutTask!!.cancel(false)
            timeOutTask = null
        }
    }

    /**
     * 是否永久性buff
     * @return
     */
    val isPermanent: Boolean
        get() = this.duration < 0

    fun destroy() {
        onDestroy()
    }

    open fun onDestroy() {
        GameContext.buffService().removeBuff(owner!!, this)
    }

    fun getActiveTime(): Long = activeTime

    fun setActiveTime(activeTime: Long) {
        this.activeTime = activeTime
    }

}
