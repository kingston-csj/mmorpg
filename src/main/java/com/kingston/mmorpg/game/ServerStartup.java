
package com.kingston.mmorpg.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kingston.mmorpg.framework.net.socket.ServerNode;
import com.kingston.mmorpg.framework.net.socket.transport.GameServer;
import com.kingston.mmorpg.framework.net.socket.transport.WebSocketServer;
import com.kingston.mmorpg.game.http.HttpServer;

@SpringBootApplication
public class ServerStartup implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

	private static ConfigurableApplicationContext context;

	private List<ServerNode> servers = new ArrayList<>();

	public static void main(String[] args) throws Exception {

		SpringApplication app = new SpringApplication(ServerStartup.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

	public void start() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");

		ServerNode socketServer = new GameServer();
		servers.add(socketServer);

		ServerNode webSocketServer = new WebSocketServer();
		servers.add(webSocketServer);

		ServerNode httpServer = new HttpServer();
		servers.add(httpServer);

		for (ServerNode node : servers) {
			node.init();
			node.start();
		}

	}

	public void stop() {
		context.close();

		try {
			for (ServerNode node : servers) {
				node.shutDown();
			}
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	@Override
	public void run(String... args) throws Exception {
		final ServerStartup server = new ServerStartup();
		server.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				server.stop();
			}
		});
	}

}
