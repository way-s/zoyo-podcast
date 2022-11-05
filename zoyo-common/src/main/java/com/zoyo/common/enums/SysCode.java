package com.zoyo.common.enums;

import com.zoyo.common.vo.IErrorCode;
import lombok.AllArgsConstructor;

/**
 * @Author: mxx
 * @Description:
 */
@AllArgsConstructor
public enum SysCode implements IErrorCode {
    /**
     * 系统常量对象
     */
    SUCCESS(200, "请求成功"),
    PARAM_ERROR(400, "请求失败"),
    TOKEN_INVALID(401, "请重新登录"),
    AUTH_ERROR(402, "系统错误"),
    PERMISSION_ERROR(403, "没有权限"),
    VALIDATE_FAILED(404, "参数检验失败"),
    METHOD_NOT_SUPPORT(405, "请求方法错误"),
    FAILED(500, "请求失败");

    private final long code;
    private final String msg;

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
