package org.forfun.mmorpg.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
//@PropertySource({ "classpath:server.properties" })
@DependsOn("serverConfigFactory")
public class ServerConfig {

	/** 服务器端口 */
	@Value("${socket.id:0}")
	private int serverId;

	/** 服务器ip */
	@Value("${socket.serverIp}")
	private String serverIp;

	/** 服务器端口 */
	@Value("${socket.port:0}")
	private int serverPort;

	/** rpc内部端口 */
	@Value("${rpc.port}")
	private int rpcPort;

	/** webSocket端口 */
	@Value("${webSocket.port:0}")
	private int webSocketPort;

	/** 管理后台服务端口 */
	@Value("${http.port:0}")
	private int httpPort;

	/** 管理后台ip白名单列表 */
	@Value("${admin.http.ips}")
	private String adminIps;

	private Set<String> adminWhiteIps;

	public Set<String> getAdminIps() {
		if (adminWhiteIps == null) {
			Set<String> tmpIps = new HashSet<>();
			for (String ip : adminIps.split(";")) {
				tmpIps.add(ip);
			}
			adminWhiteIps = tmpIps;
		}
		return adminWhiteIps;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

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

	public int getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(int rpcPort) {
		this.rpcPort = rpcPort;
	}

	public int getWebSocketPort() {
		return webSocketPort;
	}

	public void setWebSocketPort(int webSocketPort) {
		this.webSocketPort = webSocketPort;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public void setAdminIps(String adminIps) {
		this.adminIps = adminIps;
	}

	public Set<String> getAdminWhiteIps() {
		return adminWhiteIps;
	}

	public void setAdminWhiteIps(Set<String> adminWhiteIps) {
		this.adminWhiteIps = adminWhiteIps;
	}

}
