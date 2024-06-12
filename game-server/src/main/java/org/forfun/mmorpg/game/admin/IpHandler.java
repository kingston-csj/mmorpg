package org.forfun.mmorpg.game.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.forfun.mmorpg.game.base.GameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

public class IpHandler implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(IpHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Set<String> whiteIps = GameContext.getServerConfig().getAdminIps();
        String realIp = IpUtil.getRealIp(request);
        boolean isSafe = whiteIps.contains(realIp);
        if (!isSafe) {
            logger.error("ip[{}]无法访问后台命令", realIp);
        }
        return isSafe;
    }
}
