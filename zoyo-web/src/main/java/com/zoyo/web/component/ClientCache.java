package com.zoyo.web.component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.corundumstudio.socketio.SocketIOClient;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class ClientCache {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 本地缓存
     */
    private static final Map<String, HashMap<UUID, SocketIOClient>> CONCURRENT_HASH_MAP
            = new ConcurrentHashMap<>();

    /**
     * 存入本地缓存
     *
     * @param userId    用户ID
     * @param sessionId 页面sessionID
     * @param ioClient  页面对应的通道连接信息
     */
    public void saveClient(String userId, UUID sessionId, SocketIOClient ioClient) {
        if (StringUtils.isNotBlank(userId)) {
            HashMap<UUID, SocketIOClient> sessionIdClientCache = CONCURRENT_HASH_MAP.get(userId);

            if (sessionIdClientCache == null) {
                sessionIdClientCache = new HashMap<>(16);
            }
            // 将在线用户的id添加到redis中
            redisUtil.zAdd(RedisConstant.SOCKET_ONLINE, userId, 1);
            sessionIdClientCache.put(sessionId, ioClient);
            CONCURRENT_HASH_MAP.put(userId, sessionIdClientCache);
        }
    }

    /**
     * 根据用户ID获取所有通道信息
     *
     * @param userId 客户端id
     * @return map
     */
    public HashMap<UUID, SocketIOClient> getUserClient(String userId) {
        return CONCURRENT_HASH_MAP.get(userId);
    }

    /**
     * 返回所有的在线用户数据
     *
     * @return map
     */
    public Map<String, HashMap<UUID, SocketIOClient>> getUserClients() {
        return CONCURRENT_HASH_MAP;
    }

    /**
     * 根据用户ID及页面sessionID删除页面链接信息
     *
     * @param userId    客户端id
     * @param sessionId sessionId
     */
    public void deleteSessionClient(String userId, UUID sessionId) {
        CONCURRENT_HASH_MAP.get(userId).remove(sessionId);
        HashMap<UUID, SocketIOClient> map = CONCURRENT_HASH_MAP.get(userId);
        if (map.size() < 1) {
            redisUtil.zRemove(RedisConstant.SOCKET_ONLINE, userId);
        }
    }

}
