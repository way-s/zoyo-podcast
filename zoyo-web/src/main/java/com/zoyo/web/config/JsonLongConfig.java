package com.zoyo.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @Author: mxx
 * @Description:
 */
@Configuration
public class JsonLongConfig {

    /**
     * 前端 long 类型精度丢失
     * <p>
     * 解决办法有很多，大体分为两种，写个全局转换器和在bean主键id上添加注解，
     * 其实解决的本质是将Long类型在序列化json时转为String字符串类型
     *
     * @param builder builder
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 全局配置序列化 返回 JSON 处理
        SimpleModule simpleModule = new SimpleModule();
        // Long -> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
    
}
