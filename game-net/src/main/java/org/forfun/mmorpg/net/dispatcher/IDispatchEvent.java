package org.forfun.mmorpg.net.dispatcher;

public interface IDispatchEvent extends IDispatch {

	String getName();

	/**
	 * 业务执行
	 */
	void action();

	/**
	 * 业务执行前后的触发点
	 */
	void run();

}
