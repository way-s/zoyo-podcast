package com.zoyo.web.config.mq;

import com.zoyo.common.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mxx
 * @Description: 创建 topic 交换机
 */
@Configuration
public class RabbitBindingConfig {

    /**
     * 创建交换机
     */
    public TopicExchange topicExchange() {
        return new TopicExchange(QueueEnum.TOPIC_QUEUE_NAME.getExchange());
    }

    /**
     * 创建队列
     */
    @Bean
    public Queue topicQueue() {
        return new Queue(QueueEnum.TOPIC_QUEUE_NAME.getName());
    }

    /**
     * 绑定 交换机与队列
     */
    @Bean
    public Binding topicBinding() {
        return BindingBuilder
                .bind(topicQueue())
                .to(topicExchange())
                .with(QueueEnum.TOPIC_QUEUE_NAME.getRouteKey());
    }

}
