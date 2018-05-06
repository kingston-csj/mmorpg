package com.kingston.mmorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.kingston.mmorpg.framework.net.socket.transport.GameServer;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.http.HttpServer;

public class ServerStartup {

	private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

	private static ConfigurableApplicationContext context;

	private static HttpServer httpServer = new HttpServer();

	public static void main(String[] args) throws Exception {
		final ServerStartup server = new ServerStartup();
		server.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				server.stop();
			}
		});
	}

	public void start() throws Exception {
		context = new FileSystemXmlApplicationContext("config/applicationContext.xml");
		ServerConfig serverConfig = SpringContext.getServerConfig();
		
//		httpServer.start(serverConfig.getHttpPort());
		new GameServer().bind(serverConfig.getServerPort());
	}


	public void stop() {
		context.close();
	}

}
