package com.kingston.mmorpg.framework.net.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class BaseTask extends AbstractDispatchTask implements Delayed {

	/** 分发地图 */
	protected int dispatchMap;
	/** 分发线 */
	protected int dispatchLine;
	/** 延迟执行的时间戳 */
	private long delayTime;

	/**
	 * 分发地图
	 */
	public int dispatchMap() {
		return dispatchMap;
	}

	/**
	 * 分发线
	 */
	public int dispatchLine() {
		return dispatchLine;
	}

	public int getWorkerId() {
		return DispatchComputer.getWorker(dispatchMap, dispatchLine);
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public int compareTo(Delayed o) {
		BaseTask otherTask = (BaseTask) o;
		long t1 = getDelay(TimeUnit.NANOSECONDS);
		long t2 = otherTask.getDelay(TimeUnit.NANOSECONDS);

		return t1 > t2 ? 1 : t1 < t2 ? -1 : 0;
	}
}
