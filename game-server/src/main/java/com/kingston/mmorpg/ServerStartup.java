
package com.kingston.mmorpg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kingston.mmorpg.framework.net.ServerNode;
import com.kingston.mmorpg.framework.net.socket.netty.NettySocketServer;
import com.kingston.mmorpg.framework.net.socket.netty.WebSocketServer;

/**
 * sprint-boot自动bean扫描只能扫描启动类的子目录，所以该类的包路径不能太深
 * 
 * @author kingston
 *
 */
@SpringBootApplication
public class ServerStartup implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(ServerStartup.class);

	private List<ServerNode> servers = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ServerStartup.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	public void start() throws Exception {
		ServerNode socketServer = new NettySocketServer();
		servers.add(socketServer);

		ServerNode webSocketServer = new WebSocketServer();
		servers.add(webSocketServer);

		for (ServerNode node : servers) {
			node.init();
			node.start();
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

	public void stop() {
		try {
			for (ServerNode node : servers) {
				node.shutDown();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
