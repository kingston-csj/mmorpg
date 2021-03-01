package com.kingston.mmorpg.net.command;

public abstract class AbstractDispatchCommand implements com.kingston.mmorpg.net.command.IDispatchTask {

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
