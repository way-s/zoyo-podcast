package com.zoyo.common.vo;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@ToString
public class RegisterParam implements Serializable {

    private static final long serialVersionUID = 2757335271428633439L;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

}
