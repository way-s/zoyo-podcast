package com.zoyo.web.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: mxx
 * @Description: 获取 HttpServletRequest 工具类
 */
public class HttpContextUtils {

    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.
                requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求中的 token
     *
     * @return token
     */
    public static String getToken() {
        return getHttpServletRequest().getHeader("Authorization");
    }

}
