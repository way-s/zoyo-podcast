package com.zoyo.web.util;

import cn.hutool.core.convert.Convert;
import com.zoyo.common.bo.JwtConfigure;
import com.zoyo.common.bo.JwtUser;
import com.zoyo.common.constant.SysConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class JwtUtil {

    @Resource
    private JwtConfigure jwtConfigure;

    /**
     * 创建token
     *
     * @param user userinfo
     * @return token
     */
    public String createToken(JwtUser user) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(SysConstant.ACCOUNT, user.getAccount());
        claims.put(SysConstant.ID, user.getId());

        Long effectiveDuration = jwtConfigure.getExpiration();
        long expiration = System.currentTimeMillis() + (effectiveDuration * 1000);
        String token = Jwts.builder()
                // 签名算法 公钥
                .signWith(SignatureAlgorithm.HS256, jwtConfigure.getSecret())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiration))
                .compact();
        // 加密 token
        return EncryptionUtil.encrypt(token);
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库或者缓存中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String account = getUserFromToken(token, JwtUser::getAccount);
        return account.equals(userDetails.getUsername()) && !isExpiration(token);
    }

    /**
     * 获取token中的信息
     *
     * @param token          token
     * @param claimsResolver 自定义函数
     * @param <T>            JwtUser
     * @return JwtUser
     */
    public <T> T getUserFromToken(String token, Function<JwtUser, T> claimsResolver) {
        final Claims claims = getTokenBody(token);
        if (claims == null) {
            return null;
        }
        JwtUser user = Convert.convert(JwtUser.class, claims);
        return claimsResolver.apply(user);
    }

    /**
     * 验证是否过期
     *
     * @param token token
     * @return boolean
     */
    public boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public Claims getTokenBody(String token) {
        Claims claims = null;
        String replaceToken = "";
        // 解密 token
        if (token.contains(jwtConfigure.getTokenHead())) {
            replaceToken = token.replace(jwtConfigure.getTokenHead(), "");
        } else {
            replaceToken = token;
        }
        // 替换 token前缀
        String decryptToken = EncryptionUtil.decryptStr(replaceToken);
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfigure.getSecret())
                    .parseClaimsJws(decryptToken)
                    .getBody();
        } catch (Exception e) {
            log.error("解析token：{} ，错误，原因为: {}", decryptToken, e.getMessage());
        }
        return claims;
    }
}
