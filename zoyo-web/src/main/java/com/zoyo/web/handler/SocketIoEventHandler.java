package com.zoyo.web.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.zoyo.common.bo.JwtUser;
import com.zoyo.common.constant.SysConstant;
import com.zoyo.web.component.ClientCache;
import com.zoyo.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: mxx
 * @Description: socket io消息处理工具类
 */
@Slf4j
@Component
public class SocketIoEventHandler {

    @Resource
    private ClientCache clientCache;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 客户端连接
     *
     * @param client 客户端
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("ws");
        String userAccount = jwtUtil.getUserFromToken(token, JwtUser::getAccount);
        if (userAccount == null) {
            client.sendEvent("error", "401");
        } else {
            Long id = jwtUtil.getUserFromToken(token, JwtUser::getId);
            UUID sessionId = client.getSessionId();
            clientCache.saveClient(id.toString(), sessionId, client);
            log.info("{} 用户上线，sessionId :{}", userAccount, sessionId);
        }
    }

    /**
     * 客户端断开
     *
     * @param client 客户端
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("ws");
        String userAccount = jwtUtil.getUserFromToken(token, JwtUser::getAccount);
        Long id = jwtUtil.getUserFromToken(token, JwtUser::getId);
        if (StringUtils.isNotBlank(userAccount)) {
            clientCache.deleteSessionClient(id.toString(), client.getSessionId());
            UUID sessionId = client.getSessionId();
            log.info("{} 用户下线，sessionId :{}", userAccount, sessionId);
        }
    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     *
     * @param client  客户端
     * @param request request
     */
    @OnEvent(value = "reply")
    public void onEvent(SocketIOClient client, AckRequest request, Object data) {
        String token = client.getHandshakeData().getSingleUrlParam("ws");
        String userAccount = jwtUtil.getUserFromToken(token, JwtUser::getAccount);
        Long userId = jwtUtil.getUserFromToken(token, JwtUser::getId);
        log.info("接收到{}的消息：{}", userAccount, data);
        // 返回消息给客户端
        pushToOnlineUser(userId, SysConstant.WS_REVERSION_PASSAGE,
                SysConstant.WE_REVERSION_MSG);
    }

    /**
     * 单发
     *
     * @param userId    userId
     * @param eventName 事件名
     * @param data      data
     */
    public void pushToOnlineUser(Object userId, String eventName, Object data) {
        HashMap<UUID, SocketIOClient> userClient = clientCache.getUserClient(userId.toString());
        if (userClient != null) {
            userClient.forEach((uuid, client) -> {
                if (client.isChannelOpen()) {
                    log.info("uuid :{}", uuid);
                    //向客户端推送消息
                    client.sendEvent(eventName, data);
                }
            });
        }
    }

    /**
     * 群发
     *
     * @param data data
     */
    public void pushToOnlineUsers(Object data) {
        Map<String, HashMap<UUID, SocketIOClient>> userClients = clientCache.getUserClients();
        if (userClients != null) {
            for (HashMap<UUID, SocketIOClient> user : userClients.values()) {
                user.forEach((uuid, client) -> {
                    if (client.isChannelOpen()) {
                        log.info("uuid :{}", uuid);
                        //向客户端推送消息
                        client.sendEvent(SysConstant.WS_NOTICE, "服务端推送消息" + DateUtil.now() + "\n");
                    }
                });
            }
        }
    }
}
