package com.zoyo.web.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: mxx
 * @Description: 获取请求ip工具类
 */
@Slf4j
public class IpUtils {

    /**
     * 获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null, unknown = "unknown", seperator = ",";
        int maxLength = 15;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtils getIpAddr() ERROR ", e);
        }

        // 使用代理，则获取第一个IP地址
        if (StringUtils.isBlank(ip)) {
            assert ip != null;
            if (ip.length() > maxLength) {
                int idx = ip.indexOf(seperator);
                if (idx > 0) {
                    ip = ip.substring(0, idx);
                }
            }
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : (ip);
    }

    public static String getIpaddr() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpAddr(request);
    }
}
