package com.kingston.mmorpg.game.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LoggerSystem {

	/** system exception */
	EXCEPTION,
	/** http admin */
	HTTP_COMMAND,
	/** server monitor */
	MONITOR,

	;

	public Logger getLogger() {
		return LoggerFactory.getLogger(this.name());
	}

}
