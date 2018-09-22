package com.kingston.mmorpg.framework.net.socket;

/**
 * 
 * @author kingston
 *
 *
 */
public interface ServerNode {
	
	
	void init();
	
	/**
	 *  服务启动
	 * @throws Exception
	 */
	void start() throws Exception;
	
	/**
	 * 服务关闭
	 * @throws Exception
	 */
	void shutDown() throws Exception;
	

}
