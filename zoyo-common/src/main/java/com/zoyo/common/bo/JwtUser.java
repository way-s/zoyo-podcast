package com.zoyo.common.bo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@NoArgsConstructor
public class JwtUser implements Serializable {

    private static final long serialVersionUID = 3918418903508650617L;

    /**
     * id
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
     * 到期时间
     */
    private long exp;
    /**
     * 开始时间
     */
    private long iat;

}
