package com.zoyo.web.service;

import com.zoyo.common.po.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.vo.LoginParam;
import com.zoyo.common.vo.RegisterParam;
import com.zoyo.common.vo.Result;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IUserService extends IService<UserEntity> {
    /**
     * 登录
     *
     * @param loginParam loginParam
     * @return Result
     */
    Result<Object> login(LoginParam loginParam);

    /**
     * 注册
     *
     * @param param param
     * @return Result
     */
    Result<Object> register(RegisterParam param);

    /**
     * 退出登录
     *
     * @return Result
     */
    Result<Object> doLogout();

    /**
     * 获取用户基本信息
     *
     * @return userBaseInfo
     */
    Result<Object> getUserBaseInfo();
}
