package com.zoyo.web.component;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class SocketIoRun implements CommandLineRunner {

    private final SocketIOServer ioServer;

    @Autowired
    public SocketIoRun(SocketIOServer ioServer) {
        this.ioServer = ioServer;
    }

    @Override
    public void run(String... args) throws Exception {
        ioServer.start();
        log.info("socket.io启动成功！");
    }
}

