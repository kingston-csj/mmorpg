package com.kingston.mmorpg.game.monitor.jmx;

import javax.management.MXBean;

/**
 * JMX游戏管理
 * 
 * @author kingston
 */
@MXBean
public interface GameMonitorMBean {

	/**
	 * total online players count
	 * 
	 * @return
	 */
	int getOnlinePlayerSum();

	/**
	 * print server state detail, including memory, thread, buff
	 * 
	 * @return
	 */
	String printServerState();

	/**
	 * execute groovy code
	 * 
	 * @param groovyCode
	 * @return
	 */
	String execGroovyScript(String groovyCode);

}
