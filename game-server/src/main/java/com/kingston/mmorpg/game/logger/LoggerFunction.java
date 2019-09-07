package com.kingston.mmorpg.game.logger;

import org.slf4j.Logger;

/**
 * 业务后台日志
 */
public enum LoggerFunction {

	/** 活动相关 */
	ACTIVITY,

	;


	public Logger getLogger() {
		return DynamicLoggerBuilder.getLogger(this.name().toLowerCase());
	}


}