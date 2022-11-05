package com.zoyo.web.component;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: mxx
 * @Description: mq消息发送者
 */
@Slf4j
@Component
public class MqSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送msg
     *
     * @param exchange   交换机
     * @param routingKey routingKey
     * @param data       data
     */
    public void sendMsg(String exchange, String routingKey, Object data) {
        log.info("{} mq分发消息", DateUtil.now());
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
    }

}
