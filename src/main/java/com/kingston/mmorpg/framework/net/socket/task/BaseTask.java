package com.kingston.mmorpg.framework.net.socket.task;

public abstract class BaseTask extends AbstractDispatchTask {

	/** 分发地图 */
	protected int dispatchMap;
	/** 分发线 */
	protected int dispatchLine;

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

}
