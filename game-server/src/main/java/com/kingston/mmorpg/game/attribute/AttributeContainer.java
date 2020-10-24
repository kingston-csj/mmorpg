package com.kingston.mmorpg.game.attribute;

import java.util.HashMap;
import java.util.Map;

public class AttributeContainer {

	
	private Map<AttributeType, Long> attrs = new HashMap<>();
	
	
	public long getAttrValue(AttributeType type) {
		return attrs.getOrDefault(type, 0L);
	}
	
	
}
