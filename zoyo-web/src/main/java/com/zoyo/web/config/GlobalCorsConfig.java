package com.zoyo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author: mxx
 * @Description: 全局跨域配置
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        // CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 允许跨域请求的域名
        config.addAllowedOriginPattern("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许跨越发送cookie
        config.setAllowCredentials(true);
        // 允许的方法
        config.addAllowedMethod("*");
        // 注册，添加映射路径
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
