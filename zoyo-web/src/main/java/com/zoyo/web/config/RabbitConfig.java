package com.zoyo.web.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mxx
 * @Description: rabbitMq 配置类
 */
@Configuration
public class RabbitConfig {

    /**
     * RabbitMQ 提供了 Jackson2JsonMessageConverter 来支持消息内容 JSON 序列化与反序列化
     *
     * @param connectionFactory 连接工厂
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
