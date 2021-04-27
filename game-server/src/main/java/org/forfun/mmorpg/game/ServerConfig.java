package org.forfun.mmorpg.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
//@PropertySource({ "classpath:server.properties" })
@Getter
@Setter
@DependsOn("serverConfigFactory")
public class ServerConfig {

	/** 服务器端口 */
	@Value("${socket.id}")
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

}
