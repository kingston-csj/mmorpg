package org.forfun.mmorpg.game.gm

import jforgame.socket.share.message.MessageExecutor
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.logger.LoggerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
open class GmDispatcher {

    @Autowired()
    @Qualifier("dataConversionService")
    private lateinit var conversionService: ConversionService

    /** [methodName, CmdExecutor] */
    companion object {
        private val GM_HANDLERS = mutableMapOf<String, MessageExecutor>()
    }

    fun registerHandler(key: String, executor: MessageExecutor) {
        GM_HANDLERS[key] = executor
    }

    fun dispatch(player: PlayerEnt, args: Array<String>) {
        val method = args[0]

        for ((targetMethod, executor) in GM_HANDLERS) {
            if (method.equals(targetMethod, ignoreCase = true)) {
                try {
                    // 动态参数要求只能有两个参数，第二个需要是 String[] 类型
                    if (isDynamicParams(executor.method)) {
                        val dynParams = Array(args.size - 1) { i -> args[i + 1] }
                        executor.method.invoke(executor.handler, player, dynParams)
                    } else {
                        val methodParams = arrayOfNulls<Any>(args.size)
                        methodParams[0] = player
                        for (i in 1 until methodParams.size) {
                            methodParams[i] = conversionService.convert(args[i], executor.params[i])
                        }
                        executor.method.invoke(executor.handler, *methodParams)
                    }
                } catch (e: Exception) {
                    LoggerUtils.error("", e)
                }
                break
            }
        }
    }

    private fun isDynamicParams(method: Method): Boolean {
        val paramSum = method.parameterCount
        return if (paramSum <= 1) {
            false
        } else {
            method.parameterTypes[paramSum - 1].isArray
        }
    }
}
