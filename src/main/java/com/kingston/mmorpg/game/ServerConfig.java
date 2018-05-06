package com.kingston.mmorpg.game;

public class ServerConfig {
	
	/** 服务器ip */
	private String serverIp;
	/** 服务器端口 */
	private int serverPort;
	/** 后台服务端口 */
	private int httpPort;
	
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
	
	
}
