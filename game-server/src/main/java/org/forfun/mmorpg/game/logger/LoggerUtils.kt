package org.forfun.mmorpg.game.logger

import jforgame.commons.util.DateUtil
import jforgame.commons.util.JsonUtil
import jforgame.logger.LoggerUtil
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import java.util.Date

object LoggerUtils {

    /**
     * Log an exception at the ERROR level with an accompanying message.
     */
    fun error(errMsg: String, e: Exception) {
        LoggerUtil.error("", e)
    }

    fun error(format: String, vararg arguments: Any) {
        LoggerUtil.error(format, *arguments)
    }

    fun info(logger: LoggerFunction, vararg args: Any) {
        if (args.isEmpty()) {
            return
        }
        require(args.size % 2 == 0) { String.format("logger %s, args %s", logger, JsonUtil.object2String(args)) }
        val sb = StringBuilder()
        sb.append("time|").append(System.currentTimeMillis()).append("|")
        var i = 0
        val n = args.size
        while (i < n) {
            val key = args[i] as String
            val value = args[i + 1]
            sb.append(key).append("|").append(value).append("|")
            i += 2
        }
        sb.deleteCharAt(sb.length - 1)
        LoggerUtil.info(sb.toString())
    }

    fun logPlayer(logger: LoggerFunction, player: PlayerEnt, vararg args: Any) {
        // 拓展args参数
        val newArgs = arrayOfNulls<Any>(args.size + 8)
        newArgs[0] = "time"
        newArgs[1] = System.currentTimeMillis()
        newArgs[2] = "date"
        newArgs[3] = DateUtil.format(Date())
        newArgs[4] = "playerId"
        newArgs[5] = player.id
        System.arraycopy(args, 0, newArgs, 8, args.size)
        LoggerUtil.info(logger.name, *newArgs)
    }
}
