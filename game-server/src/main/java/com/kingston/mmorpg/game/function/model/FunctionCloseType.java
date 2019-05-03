package com.kingston.mmorpg.game.function.model;

public enum FunctionCloseType {
	
	/**
	 * 等级
	 */
	LEVEL(1),
	
	;
	
	int type;
	
	FunctionCloseType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

}
