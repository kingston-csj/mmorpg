package org.forfun.mmorpg.client;

import org.forfun.mmorpg.game.ConfigScanPaths;
import org.forfun.mmorpg.net.message.MessageFactory;

/**
 * 客户端模拟器启动程序
 *
 */
public class ClientStartup {

	public static void main(String[] args) throws Exception {
		MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);
		
		SocketClient client = new SocketClient();
		client.start();
	}

}
