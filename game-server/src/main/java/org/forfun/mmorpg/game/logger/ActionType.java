package org.forfun.mmorpg.game.logger;

/**
 * 日志原因
 * 
 *
 *
 */
public enum ActionType {
	
	UseItem(100, "使用道具"),
	
	;
	
	int id;
	
	String description;

	private ActionType(int id, String description) {
		this.id = id;
		this.description = description;
	}

}
