package com.zoyo.web.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.bo.JwtConfigure;
import com.zoyo.common.bo.JwtUser;
import com.zoyo.common.bo.UserBo;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.exception.Asserts;
import com.zoyo.common.po.RoleEntity;
import com.zoyo.common.po.UserEntity;
import com.zoyo.common.vo.LoginParam;
import com.zoyo.common.vo.RegisterParam;
import com.zoyo.common.vo.Result;
import com.zoyo.common.vo.UserInfoVo;
import com.zoyo.web.mapper.RoleMapper;
import com.zoyo.web.mapper.UserMapper;
import com.zoyo.web.service.IUserService;
import com.zoyo.web.util.CurrentSubject;
import com.zoyo.web.util.IpUtils;
import com.zoyo.web.util.JwtUtil;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtConfigure jwtConfigure;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private CurrentSubject currentSubject;

    @Resource
    private BCryptPasswordEncoder pwdEncoder;

    /**
     * 登录
     * 1. 查数据库
     * 2. 组合userVo
     * 3. 存放 redis
     * 4. 返回 token
     *
     * @param loginParam loginParam
     * @return Result
     */
    @Override
    public Result<Object> login(LoginParam loginParam) {
        UserEntity user = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getAccount, loginParam.getAccount())
        );

        if (user == null) {
            Asserts.fail("账号不存在，请先注册");
        } else if (user.getStatus() == 0) {
            Asserts.fail("你已被禁封，请联系管理员");
        } else if (!pwdEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            Asserts.fail("密码不正确");
        }

        RoleEntity roles = this.roleMapper.selectOne(new LambdaQueryWrapper<RoleEntity>()
                .select(RoleEntity::getRole)
                .eq(RoleEntity::getUserId, user.getId())
        );

        UserBo userBo = Convert.convert(UserBo.class, user);
        userBo.setRole(roles.getRole());

        redisUtil.set(RedisConstant.FM_WEB_USER + user.getAccount(),
                userBo, jwtConfigure.getExpiration());

        this.update(new LambdaUpdateWrapper<UserEntity>()
                .set(UserEntity::getLoginIp, IpUtils.getIpaddr())
                .set(UserEntity::getLoginDate, new Date())
                .eq(UserEntity::getId, userBo.getId())
        );

        // 创建token
        JwtUser jwtUser = new JwtUser();
        jwtUser.setAccount(loginParam.getAccount());
        jwtUser.setId(userBo.getId());

        String token = jwtUtil.createToken(jwtUser);
        return Result.ok(token);
    }

    @Override
    public Result<Object> register(RegisterParam param) {
        UserEntity one = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getAccount, param.getAccount())
        );
        if (one != null) {
            Asserts.fail("账号已存在");
        }

        UserEntity existNickName = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getNickName, param.getNickname())
        );
        if (existNickName != null) {
            Asserts.fail("昵称已存在");
        }

        UserEntity user = new UserEntity()
                .setAccount(param.getAccount())
                .setNickName(param.getNickname())
                .setPassword(pwdEncoder.encode(param.getPassword()));
        // 保存
        this.save(user);

        // 新增权限
        RoleEntity userRole = new RoleEntity()
                .setUserId(user.getId());
        this.roleMapper.insert(userRole);

        return Result.ok();
    }

    @Override
    public Result<Object> doLogout() {
        String account = currentSubject.getUserAccount();
        log.info("account {}，登出", account);
        // 下线
        redisUtil.del(RedisConstant.FM_WEB_USER + account);
        return Result.ok();
    }

    @Override
    public Result<Object> getUserBaseInfo() {
        UserBo userInfo = currentSubject.getUserInfo();
        if (userInfo != null) {
            UserInfoVo userInfoVo = Convert.convert(UserInfoVo.class, userInfo);
            return Result.ok(userInfoVo);
        } else {
            String account = currentSubject.getUserAccount();
            UserEntity user = this.getOne(new LambdaQueryWrapper<UserEntity>()
                    .select(UserEntity::getAccount, UserEntity::getNickName,
                            UserEntity::getAvatar)
                    .eq(UserEntity::getAccount, account)
            );
            UserInfoVo userInfoVo = Convert.convert(UserInfoVo.class, user);
            return Result.ok(userInfoVo);
        }
    }


}
