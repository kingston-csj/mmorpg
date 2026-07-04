package org.forfun.mmorpg.game.base

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jforgame.commons.thread.NamedThreadFactory
import org.forfun.mmorpg.game.logger.LoggerUtils
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@Component
open class SchedulerManager {

    companion object {
        @JvmStatic
        var instance: SchedulerManager? = null
    }

    private lateinit var service: ScheduledExecutorService

    @PostConstruct
    private fun init() {
        service = Executors.newScheduledThreadPool(2, NamedThreadFactory("common-scheduler"))
        instance = this
    }

    /**
     * @param command
     * @param initialDelay 毫秒数
     * @param period       毫秒数
     * @return
     */
    fun scheduleAtFixedRate(
        command: Runnable,
        initialDelay: Long,
        period: Long
    ): ScheduledFuture<*> {
        return service.scheduleAtFixedRate(LogTask(command), initialDelay, period, TimeUnit.MILLISECONDS)
    }

    /**
     * @param command
     * @param delay   延迟毫秒数
     * @return
     */
    fun schedule(command: Runnable, delay: Long): ScheduledFuture<*> {
        return service.schedule(LogTask(command), delay, TimeUnit.MILLISECONDS)
    }

    @PreDestroy
    fun shutDown() {
        service.shutdown()
        service.shutdownNow()
        LoggerUtils.error("定时器关闭结束")
    }

    private class LogTask(private val wrapper: Runnable) : Runnable {
        override fun run() {
            try {
                wrapper.run()
            } catch (e: Exception) {
                LoggerUtils.error("定时任务执行异常", e)
            }
        }
    }
}
