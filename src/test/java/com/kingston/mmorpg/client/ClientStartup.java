package com.kingston.mmorpg.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 客户端模拟器启动程序
 * @author kingston
 */
public class ClientStartup {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SocketClient client = new SocketClient();
		client.start();
	}

}
