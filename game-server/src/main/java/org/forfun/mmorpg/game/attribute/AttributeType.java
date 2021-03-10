package org.forfun.mmorpg.game.attribute;

public enum AttributeType {
	
	Attack(1, Boolean.FALSE),
	
	Defense(1, Boolean.FALSE),
	
	Hp(1, Boolean.FALSE),
	
	
	;
	
	
	int id;
	/**
	 * 是否万分比
	 */
	boolean percent;
	
	AttributeType(int id, boolean percent) {
		this.id = id;
		this.percent = percent;
	}

}
