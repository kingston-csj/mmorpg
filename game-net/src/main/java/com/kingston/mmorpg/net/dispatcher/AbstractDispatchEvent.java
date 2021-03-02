package com.kingston.mmorpg.net.dispatcher;

public abstract class AbstractDispatchEvent implements IDispatchEvent {

	public String getName() {
		return getClass().getSimpleName();
	}

	protected long startTime;

	/**
	 * 业务执行前后的触发点
	 */
	public void run() {
		startTime = System.currentTimeMillis();
		action();
	}

	public long getStartTime() {
		return startTime;
	}

}
