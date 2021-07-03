package org.forfun.mmorpg.framework.eventbus;

/**
 * 事件基类
 *
 */
public interface BaseEvent {

	default Object getOwner() {
		return "system";
	}

}
