package com.zoyo.web.config;

import com.zoyo.common.bo.UrlsConfigure;
import com.zoyo.web.component.CustomUserDetailsServiceImpl;
import com.zoyo.web.handler.JwtAuthenticationTokenFilter;
import com.zoyo.web.handler.NoPermissionHandler;
import com.zoyo.web.handler.RestAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author: mxx
 * @Description: WebSecurity 配置
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private NoPermissionHandler noPermissionHandler;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private CustomUserDetailsServiceImpl userDetailsService;

    @Resource
    private UrlsConfigure urlsConfigure;

    /**
     * 密码加密器
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器，用户的来源
     *
     * @param auth 访问者
     * @throws Exception exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 安全策略
     * authorizeRequests url拦截配置
     * hasRole，hasAuthority：是角色授权，授权代码；两者功能一样，hasRole会给role角色添加ROLE_前缀。
     * permitAll 总是返回true，表示允许所有的
     *
     * @param http 请求
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.authorizeRequests();
        // 不需要认证的资源路径
        for (String url : urlsConfigure.getIgnoredUrls()) {
            registry.antMatchers(url).permitAll();
        }

        for (String url : urlsConfigure.getAdminUrls()) {
            registry.antMatchers(url).hasAuthority("admin");
        }

        // 设置路径权限
        // csrf 跨站请求伪造
        registry.and()
                .csrf().disable() // 由于使用的是JWT，我们这里不需要csrf，关闭 csrf 防御，前后端分离时不需要开启
                // 基于token，所以不需要session，禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 放行 option 请求，跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT 过滤
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 没有权限时 或者 未登录时自定义的返回结果
        http.exceptionHandling()
                .accessDeniedHandler(noPermissionHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

}
