package com.kingston.mmorpg.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource({ "classpath:server.properties" })
public class ServerConfig {

	/** 服务器ip */
	@Value("${socket.serverIp}")
	private String serverIp;

	/** 服务器端口 */
	@Value("${socket.port}")
	private int serverPort;

	/** webSocket端口 */
	@Value("${webSocket.port}")
	private int webSocketPort;

	/** 后台服务端口 */
	@Value("${http.port}")
	private int httpPort;

	/** 后台服务端口 */
	@Value("${admin.http.ips}")
	private String adminIps;

	private Set<String> adminWhiteIps;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getWebSocketPort() {
		return webSocketPort;
	}

	public void setWebSocketPort(int webSocketPort) {
		this.webSocketPort = webSocketPort;
	}

	public Set<String> getAdminIps() {
		if (adminWhiteIps == null) {
			Set<String> tmpIps = new HashSet<>();
			for (String ip : adminIps.split(";")) {
				tmpIps.add(ip);
				adminWhiteIps = tmpIps;
			}
		}
		return adminWhiteIps;
	}

}
