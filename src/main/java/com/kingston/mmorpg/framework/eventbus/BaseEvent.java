package com.kingston.mmorpg.framework.eventbus;

public interface BaseEvent {
	
	default Object getOwner() {
		return String.valueOf("system");
	}

}
