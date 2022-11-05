package com.zoyo.web.handler;

import com.zoyo.common.bo.JwtConfigure;
import com.zoyo.common.bo.JwtUser;
import com.zoyo.web.component.CustomUserDetailsServiceImpl;
import com.zoyo.web.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtConfigure jwtConfigure;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private CustomUserDetailsServiceImpl detailsService;

    /**
     * 过滤器验证步骤
     * 第一步：从请求头中获取token
     * 第二步：从token中获取用户信息，判断token数据是否合法
     * 第三步：校验token是否有效，包括token是否过期
     * 第四步：检验成功后将用户信息存放到SecurityContextHolder Context中
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 header请求头
        String reqHeader = request.getHeader(jwtConfigure.getTokenHeader());
        // 如果 header 不为空，且以 Bearer开头
        if (reqHeader != null && reqHeader.startsWith(jwtConfigure.getTokenHead())) {
            String token = reqHeader.replace(jwtConfigure.getTokenHead(), "");
            String authAccount = jwtUtil.getUserFromToken(token, JwtUser::getAccount);
            log.info("checking authAccount: {}", authAccount);
            if (authAccount != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据用户名（这用的是账号）查询用户权限
                UserDetails userDetails = detailsService.loadUserByUsername(authAccount);
                // 校验 token
                if (jwtUtil.validateToken(token, userDetails)) {
                    // 构建 authentication身份信息
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 设置 details
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    log.info("authenticationToken: {}", authenticationToken);
                    // 设置 authentication 到上下文对象中
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
