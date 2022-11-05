package com.zoyo.web.controller;

import com.zoyo.common.vo.LoginParam;
import com.zoyo.common.vo.RegisterParam;
import com.zoyo.common.vo.Result;
import com.zoyo.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author mxx
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * http://localhost:8888/user/hello
     * 测试接口
     *
     * @return Result
     */
    @GetMapping("/hello")
    public Object hello() {
        log.info("hello 接口被访问");
        return Result.okMsg("ok");
    }

    /**
     * 登录
     *
     * @param loginParam loginParam
     * @return Result
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginParam loginParam) {
        log.info("login param ：{}", loginParam);
        return this.userService.login(loginParam);
    }

    /**
     * 注册
     *
     * @param param rst param
     * @return Result
     */
    @PostMapping("/reg")
    public Result<Object> register(@RequestBody RegisterParam param) {
        log.info("rst ：{}", param);
        return this.userService.register(param);
    }

    /**
     * 退出登录
     *
     * @return Result
     */
    @PostMapping("/logout")
    public Result<Object> logout() {
        return this.userService.doLogout();
    }

    /**
     * 获取用户的基本信息
     *
     * @return Result
     */
    @GetMapping("/info")
    public Result<Object> userBaseInfo() {
        return this.userService.getUserBaseInfo();
    }

}
