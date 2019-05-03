package com.kingston.mmorpg.game.function.model;

/**
 * 功能开启方式
 * 
 * @author kingston
 *
 */
public enum FunctionOpenType {
	
	/**
	 * 等级
	 */
	LEVEL(1),
	
	/**
	 * 任务
	 */
	QUEST(2),
	
	;
	
	int type;
	
	FunctionOpenType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
