package com.easyconv.easyconvserver.core.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static String getIp(HttpServletRequest req){

        String clientIp = req.getHeader("X-Forwarded-For");
        if (isValidIp(clientIp)) {
            //Proxy 서버인 경우
            clientIp = req.getHeader("Proxy-Client-IP");
        }
        if (isValidIp(clientIp)) {
            //Weblogic 서버인 경우
            clientIp = req.getHeader("WL-Proxy-Client-IP");
        }
        if (isValidIp(clientIp)) {
            clientIp = req.getHeader("HTTP_CLIENT_IP");
        }
        if (isValidIp(clientIp)) {
            clientIp = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isValidIp(clientIp)) {
            clientIp = req.getRemoteAddr();
        }
        return clientIp;
    }

    private static boolean isValidIp(String clientIp){
        return StringUtils.hasLength(clientIp)|| "unknown".equalsIgnoreCase(clientIp);
    }
}
