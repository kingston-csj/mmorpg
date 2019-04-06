package com.kingston.mmorpg.framework.eventbus;

/**
 * 事件基类
 * 
 * @author kingston
 *
 */
public interface BaseEvent {

	default Object getOwner() {
		return String.valueOf("system");
	}

}
