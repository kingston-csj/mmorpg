package com.kingston.mmorpg.game.admin;

import com.kingston.mmorpg.game.base.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class IpHandler implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(IpHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Set<String> whiteIps = SpringContext.getServerConfig().getAdminIps();
        String realIp = IpUtil.getRealIp(request);
        boolean isSafe = whiteIps.contains(realIp);
        if (!isSafe) {
            logger.error("ip[{}]无法访问后台命令", realIp);
        }
        return isSafe;
    }
}
