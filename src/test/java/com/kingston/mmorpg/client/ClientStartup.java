package com.kingston.mmorpg.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 客户端模拟器启动程序
 * @author kingston
 */
public class ClientStartup {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext("config/applicationContext.xml");
		new SocketClient().start();
	}

}
