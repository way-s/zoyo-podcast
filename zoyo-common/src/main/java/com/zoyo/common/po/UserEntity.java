package com.zoyo.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author mxx
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("zy_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -5498074045946641573L;

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
     * 删除（0 存在，1 删除）
     */
    private Integer deleted;

    /**
     * 最后登录ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}
