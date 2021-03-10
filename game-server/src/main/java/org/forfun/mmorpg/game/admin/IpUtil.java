package org.forfun.mmorpg.game.admin;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {


    private static final String LOCAL_IP = "127.0.0.1";

    public static String getRealIp(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        if (StringUtils.isNotBlank(userName)) {
            return userName;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }
}
