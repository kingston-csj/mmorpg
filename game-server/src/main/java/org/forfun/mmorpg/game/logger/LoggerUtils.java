package org.forfun.mmorpg.game.logger;

import org.forfun.mmorpg.game.util.JsonUtil;
import org.slf4j.Logger;

public class LoggerUtils {

	public static void info(LoggerFunction logger, Object... args) {
		if (args.length == 0) {
			return;
		}
		if (args.length % 2 != 0) {
			error("", new IllegalArgumentException(String.format("logger %s, args %s", logger, JsonUtil.object2String(args))));
			return;
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
		logger.getLogger().info(sb.toString());
	}

	/**
	 * Log an exception at the ERROR level with an accompanying message.
	 *
	 * @param errMsg the message accompanying the exception
	 * @param e   the exception to log
	 */
	public static void error(String errMsg, Exception e) {
		Logger logger = LoggerSystem.EXCEPTION.getLogger();
		logger.error("", e);
	}

	public static void error(String format, Object... arguments) {
		Logger logger = LoggerSystem.EXCEPTION.getLogger();
		logger.error(format, arguments);
	}
}