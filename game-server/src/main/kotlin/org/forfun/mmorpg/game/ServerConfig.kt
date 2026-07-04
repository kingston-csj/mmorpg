package org.forfun.mmorpg.game

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import java.util.HashSet

@Component
@DependsOn("serverConfigFactory")
class ServerConfig {

    /** 服务器端口 */
    @Value("\${socket.id:0}")
    var serverId: Int = 0

    /** 服务器ip */
    @Value("\${socket.serverIp}")
    var serverIp: String? = null

    /** 服务器端口 */
    @Value("\${socket.port:0}")
    var serverPort: Int = 0

    /** rpc内部端口 */
    @Value("\${rpc.port}")
    var rpcPort: Int = 0

    /** webSocket端口 */
    @Value("\${webSocket.port:0}")
    var webSocketPort: Int = 0

    /** 管理后台服务端口 */
    @Value("\${http.port:0}")
    var httpPort: Int = 0

    /** 管理后台ip白名单列表 */
    @Value("\${admin.http.ips}")
    var adminIps: String? = null

    var adminWhiteIps: MutableSet<String>? = null

    val adminWhiteIpsSet: MutableSet<String>
        get() {
            if (adminWhiteIps == null) {
                val tmpIps = HashSet<String>()
                for (ip in adminIps!!.split(";")) {
                    tmpIps.add(ip)
                }
                adminWhiteIps = tmpIps
            }
            return adminWhiteIps!!
        }
}
