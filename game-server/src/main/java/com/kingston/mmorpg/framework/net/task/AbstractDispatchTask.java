package com.kingston.mmorpg.framework.net.task;

public abstract class AbstractDispatchTask implements IDispatchTask {

	public String getName() {
		return getClass().getSimpleName();
	}

	/**
	 * 业务执行前后的触发点
	 */
	public void run() {
		long start = System.currentTimeMillis();
		action();
		long end = System.currentTimeMillis();
	}

}
