package com.zoyo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: mxx
 * @Description: redis 配置
 */
@Configuration
public class RedisConfig {

    /**
     * redis 序列化配置
     *
     * @param factory RedisConnectionFactory
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setConnectionFactory(factory);
        // redis的键采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // redis的值采用jackson的序列化方式
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash的值采用jackson的序列化方式
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        // 设置默认序列化程序
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
