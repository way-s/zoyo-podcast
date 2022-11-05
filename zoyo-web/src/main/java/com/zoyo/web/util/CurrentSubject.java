package com.zoyo.web.util;

import cn.hutool.core.convert.Convert;
import com.zoyo.common.bo.JwtUser;
import com.zoyo.common.bo.UserBo;
import com.zoyo.common.constant.RedisConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: mxx
 * @Description:
 */
@Component
public class CurrentSubject {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 获取登录用户的的 token中的账号
     *
     * @return account
     */
    public String getUserAccount() {
        String token = HttpContextUtils.getToken();
        if (token != null) {
            return jwtUtil.getUserFromToken(token, JwtUser::getAccount);
        }
        return null;
    }

    /**
     * 获取 redis中登录用户的信息
     *
     * @return userinfo
     */
    public UserBo getUserInfo() {
        // 账号
        String account = getUserAccount();
        if (account != null) {
            // 获取 redis中数据
            Object userInfo = redisUtil.get(RedisConstant.FM_WEB_USER + account);
            // 转换为实体类
            return Convert.convert(UserBo.class, userInfo);
        } else {
            return null;
        }
    }

    /**
     * 获取userId
     *
     * @return userId
     */
    public Long getUserId() {
        // 账号
        String account = getUserAccount();
        if (account != null) {
            // 获取 redis中数据
            Object userInfo = redisUtil.get(RedisConstant.FM_WEB_USER + account);
            // 转换为实体类
            UserBo userBo = Convert.convert(UserBo.class, userInfo);
            return userBo.getId();
        } else {
            return null;
        }
    }

    /**
     * 获取 redis中登录管理员的信息
     *
     * @return AdminInfo
     */
    public UserBo getAdminInfo() {
        // 账号
        String account = getUserAccount();
        if (account != null) {
            // 获取 redis中数据
            Object userInfo = redisUtil.get(RedisConstant.FM_WEB_USER + account);
            // 转换为实体类
            return Convert.convert(UserBo.class, userInfo);
        } else {
            return null;
        }
    }


}
