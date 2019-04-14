package com.kingston.mmorpg.game.logger;

import org.slf4j.Logger;

public class LoggerUtils {

	/**
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void info(String format, Object... arguments) {
		Logger logger = LoggerSystem.MONITOR.getLogger();
		logger.info(format, arguments);
	}

	/**
	 * Log an exception at the ERROR level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception to log
	 */
	public static void error(String errMsg, Exception e) {
		Logger logger = LoggerSystem.EXCEPTION.getLogger();
		logger.error("", e);
	}

	/**
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	public static void error(String format, Object... arguments) {
		Logger logger = LoggerSystem.EXCEPTION.getLogger();
		logger.error(format, arguments);
	}
}