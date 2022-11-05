package com.zoyo.common.exception;

import com.zoyo.common.vo.IErrorCode;

/**
 * @Author: mxx
 * @Description: 自定义API异常
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -4920045640902366741L;

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
