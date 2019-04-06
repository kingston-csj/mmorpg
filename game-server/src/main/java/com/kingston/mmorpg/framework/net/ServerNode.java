package com.kingston.mmorpg.framework.net;

/**
 * 
 * 各类对外服务节点
 * 
 * @author kingston
 *
 */
public interface ServerNode {

	void init();

	/**
	 *  服务启动
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * 服务关闭
	 * 
	 * @throws Exception
	 */
	void shutDown() throws Exception;

}
