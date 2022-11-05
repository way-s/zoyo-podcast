package com.zoyo.web.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.zoyo.common.bo.SocketIoConfigure;
import com.zoyo.web.handler.SocketIoEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: mxx
 * @Description: socketIo 配置类
 */
@Slf4j
@Configuration
public class SocketIoConfig {

    @Resource
    private SocketIoConfigure ioConfigure;

    @Resource
    private SocketIoEventHandler eventHandler;

    @Bean
    public SocketIOServer socketIoServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config =
                new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(ioConfigure.getHost());
        config.setPort(ioConfigure.getPort());
        config.setBossThreads(ioConfigure.getBossCount());
        config.setWorkerThreads(ioConfigure.getBossCount());
        config.setAllowCustomRequests(ioConfigure.isAllowCustomRequests());
        config.setUpgradeTimeout(ioConfigure.getUpgradeTimeout());
        config.setPingTimeout(ioConfigure.getPingTimeout());
        config.setPingInterval(ioConfigure.getPingInterval());
        //该处进行身份验证
        config.setAuthorizationListener(handshakeData -> {
            return true;
        });
        SocketIOServer ioServer = new SocketIOServer(config);
        ioServer.addListeners(eventHandler);

        return ioServer;
    }

    /**
     * 开启注解，比如 @OnConnect、@OnEvent
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIoServer());
    }

}
