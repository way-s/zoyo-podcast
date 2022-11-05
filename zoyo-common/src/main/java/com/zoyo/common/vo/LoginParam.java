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
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 5879669563153497412L;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

}
