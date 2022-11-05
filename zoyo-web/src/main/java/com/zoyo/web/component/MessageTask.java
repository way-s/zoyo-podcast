package com.zoyo.web.component;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.constant.SysConstant;
import com.zoyo.common.enums.QueueEnum;
import com.zoyo.common.po.AudioSubscribeEntity;
import com.zoyo.web.handler.SocketIoEventHandler;
import com.zoyo.web.mapper.AudioSubscribeMapper;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class MessageTask {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MqSender mqSender;

    @Resource
    private SocketIoEventHandler socketIoEventHandler;

    @Resource
    private AudioSubscribeMapper subscribeMapper;

    /**
     * 向订阅者发送新消息
     */
//    @Scheduled(initialDelay = 1000 * 30, fixedDelay = 1000 * 60 * 6, zone = "GMT-8:00")
    public void sendNewMsgToSubscriber() {
        mqSender.sendMsg(QueueEnum.TOPIC_QUEUE_NAME.getExchange(),
                QueueEnum.TOPIC_QUEUE_NAME.getRouteKey(), DateUtil.now());
    }

    /**
     * 告知订阅者删除失效的rss
     */
//    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60 * 12, zone = "GMT-8:00")
    public void notificationToSubscriberDelErrorRss() {
        Set<Object> objects = redisUtil.zRangeByScore(RedisConstant.PODCAST_RSS_ERROR, 0, 5);
        List<String> list = Convert.toList(String.class, objects);
        for (String strings : list) {
            String[] split = strings.split(",");
            String feedId = split[0];
            String rssTitle = split[1];
            log.info("feedId:{}，rssTitle:{}", feedId, rssTitle);
            Boolean exist = redisUtil.hHasKey(RedisConstant.PODCAST_SUMMARY, feedId);
            log.info("exist: {}", exist);
            // 不存在，解析该 rss链接失败，告知订阅者
            if (!exist) {
                // 获取该 rss订阅者
                List<AudioSubscribeEntity> userIds = subscribeMapper.selectList(new LambdaQueryWrapper<AudioSubscribeEntity>()
                        .select(AudioSubscribeEntity::getUserId)
                        .eq(AudioSubscribeEntity::getFeedId, feedId)
                        .eq(AudioSubscribeEntity::getDeleted, 0)
                        .groupBy(AudioSubscribeEntity::getUserId)
                );
                // 向前端发送消息
                String msg = "播客名为 " + rssTitle + " 的订阅失效请删除";
                userIds.forEach(user -> {
                    socketIoEventHandler.pushToOnlineUser(user.getUserId(),
                            SysConstant.WS_ERROR_PASSAGE, msg);
                });
            }
        }
    }



}
