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
 * 管理员信息表
 * </p>
 *
 * @author mxx
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("zy_admin")
public class AdminEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
     * 头像地址
     */
    private String avatar;

    /**
     * 状态 0：停用 1：正常
     */
    private Integer status;

    /**
     * 删除（0 存在，1 删除）
     */
    private Integer deleted;

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
