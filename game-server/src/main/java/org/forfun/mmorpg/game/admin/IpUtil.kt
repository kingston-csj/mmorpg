package org.forfun.mmorpg.game.admin

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import java.net.Inet6Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.SocketAddress

object IpUtil {

    private val LOCAL_IP = "127.0.0.1"
    private val logger = LoggerFactory.getLogger(IpUtil::class.java)

    fun getRealIp(request: HttpServletRequest): String {
        val userName = request.getParameter("userName")
        if (StringUtils.isNotBlank(userName)) {
            return userName!!
        }
        var ip = request.getHeader("x-forwarded-for")
        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }

        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }

        if (ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }

        if (ip.contains(",")) {
            ip = ip.split(",")[0]
        }

        return if ("0:0:0:0:0:0:0:1" == ip) LOCAL_IP else ip
    }

}
