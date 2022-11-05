package com.zoyo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: mxx
 * @Description: 消息队列枚举配置
 */
@Getter
@AllArgsConstructor
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    TOPIC_QUEUE_NAME(
            "zy.web.tp.exchange",
            "zy.web.queue", "zy.listen.mq");

    /**
     * 交换名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String name;
    /**
     * 路由键
     */
    private final String routeKey;

}
