package com.kingston.mmorpg.framework.net.command;

public abstract class AbstractDispatchCommand implements IDispatchTask {

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
