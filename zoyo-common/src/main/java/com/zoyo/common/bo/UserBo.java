package com.zoyo.common.bo;

import com.zoyo.common.po.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserBo extends UserEntity implements Serializable {

    private static final long serialVersionUID = -6433848952853096660L;
    /**
     * 用户id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * (0 男，1 女，2 未知)
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态 0：停用 1：正常
     */
    private Integer status;

    /**
     * 最后登录ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 角色
     */
    private String role;

}
