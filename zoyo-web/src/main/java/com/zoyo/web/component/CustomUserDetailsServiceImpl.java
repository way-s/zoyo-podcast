package com.zoyo.web.component;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zoyo.common.bo.UserBo;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.exception.Asserts;
import com.zoyo.common.po.RoleEntity;
import com.zoyo.common.po.UserEntity;
import com.zoyo.web.bo.UserDetail;
import com.zoyo.web.service.IRoleService;
import com.zoyo.web.service.IUserService;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取 UserDetails 用户信息
     *
     * @param account 账号
     * @return UserDetails
     * @throws UsernameNotFoundException 未找到用户名异常
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        log.info("开始权限认证，用户账号为: {}", account);
        UserEntity user = null;
        UserDetail userDetail = null;
        // 从 redis中取数据
        Object userRedis = redisUtil.get(RedisConstant.FM_WEB_USER + account);

        if (userRedis == null || userRedis.toString().trim().length() < 1) {
            // redis 中没有数据，查数据库
            user = this.userService.getOne(new LambdaQueryWrapper<UserEntity>()
                    .eq(UserEntity::getAccount, account)
            );
            if (user == null) {
                Asserts.fail("账号不存在");
            }
            RoleEntity roles = this.roleService.getOne(new LambdaQueryWrapper<RoleEntity>()
                    .select(RoleEntity::getRole, RoleEntity::getStatus)
                    .eq(RoleEntity::getUserId, user.getId())
            );
            // 构建 userDetail 对象
            userDetail = new UserDetail()
                    .setAccount(user.getAccount())
                    .setPassword(user.getPassword())
                    .setRole(roles.getRole());
        } else {
            UserBo userBo = Convert.convert(UserBo.class, userRedis);
            // 构建 userDetail 对象
            userDetail = new UserDetail()
                    .setAccount(userBo.getAccount())
                    .setPassword(userBo.getPassword())
                    .setRole(userBo.getRole());
        }

        log.info("userDetail: {}", userDetail);
        return userDetail;
    }

}
