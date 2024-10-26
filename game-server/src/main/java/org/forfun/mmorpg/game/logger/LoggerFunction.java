package org.forfun.mmorpg.game.logger;

import org.slf4j.Logger;

/**
 * 业务后台日志
 */
public enum LoggerFunction {

	/** 活动相关 */
	ACTIVITY,

	;


	public Logger getLogger() {
		return LoggerBuilder.getLogger(this.name().toLowerCase());
	}


}