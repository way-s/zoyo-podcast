package com.zoyo.common.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * (0 男，1 女，2 未知)
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatar;
}
