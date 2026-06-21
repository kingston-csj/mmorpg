package org.forfun.mmorpg.game.admin

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.forfun.mmorpg.game.base.GameContext
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor

class IpHandler : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(IpHandler::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val whiteIps = GameContext.serverConfig().adminWhiteIpsSet
        val realIp = IpUtil.getRealIp(request)
        val isSafe = whiteIps.contains(realIp)
        if (!isSafe) {
            logger.error("ip[$realIp]无法访问后台命令")
        }
        return isSafe
    }
}
