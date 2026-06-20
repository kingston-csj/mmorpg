package org.forfun.mmorpg.game.logger;

import jforgame.commons.util.DateUtil;
import jforgame.commons.util.JsonUtil;
import jforgame.logger.LoggerUtil;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

import java.util.Date;

public class LoggerUtils {

    /**
     * Log an exception at the ERROR level with an accompanying message.
     *
     * @param errMsg the message accompanying the exception
     * @param e      the exception to log
     */
    public static void error(String errMsg, Exception e) {
        LoggerUtil.error("", e);
    }

    public static void error(String format, Object... arguments) {
        LoggerUtil.error(format, arguments);
    }

    public static void info(LoggerFunction logger, Object... args) {
        if (args.length == 0) {
            return;
        }
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("logger %s, args %s", logger, JsonUtil.object2String(args)));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("time|").append(System.currentTimeMillis()).append("|");
        for (int i = 0, n = args.length; i < n; i += 2) {
            String key = (String) args[i];
            Object value = args[i + 1];
            sb.append(key).append("|")
                    .append(value).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        LoggerUtil.info(sb.toString());
    }

    public static void logPlayer(LoggerFunction logger, PlayerEnt player, Object... args) {
        // 拓展args参数
        Object[] newArgs = new Object[args.length + 8];
        newArgs[0] = "time";
        newArgs[1] = System.currentTimeMillis();
        newArgs[2] = "date";
        newArgs[3] = DateUtil.format(new Date());
        newArgs[4] = "playerId";
        newArgs[5] = player.getId();
        System.arraycopy(args, 0, newArgs, 8, args.length);
        LoggerUtil.info(logger.name(), newArgs);
    }
}