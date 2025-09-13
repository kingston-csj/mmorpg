package org.forfun.mmorpg.game.function.model;

/**
 * 功能开启方式
 * 
 *
 *
 */
public enum FunctionOpenType {
	
	/**
	 * 等级
	 */
	Level(1),
	
	/**
	 * 任务
	 */
	Quest(2),
	
	;
	
	int type;
	
	FunctionOpenType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
