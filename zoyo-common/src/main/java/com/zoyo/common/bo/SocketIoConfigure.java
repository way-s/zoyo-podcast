package com.zoyo.common.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "socket-io")
public class SocketIoConfigure {

    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    private long maxFramePayloadLength;

    private long maxHttpContentLength;

    /**
     * socket连接数大小
     */
    private int bossCount;

    /**
     * 工作线程
     */
    private int workCount;

    /**
     * 允许自定义请求
     */
    private boolean allowCustomRequests;

    /**
     * 超时时间
     */
    private int upgradeTimeout;

    /**
     * Ping消息超时时间
     */
    private int pingTimeout;

    /**
     * Ping消息间隔（毫秒）
     */
    private int pingInterval;
}
