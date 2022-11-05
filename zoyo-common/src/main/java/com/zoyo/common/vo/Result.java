package com.zoyo.common.vo;

import com.zoyo.common.enums.SysCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "请求结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4717402924015320294L;

    @ApiModelProperty(value = "状态码")
    private long code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回的对象信息")
    private T data;

    @ApiModelProperty(value = "成功标识")
    private Boolean success = false;

    public static <T> Result<T> fail() {
        return fail(SysCode.FAILED);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>()
                .setCode(SysCode.FAILED.getCode())
                .setMsg(msg);
    }

    public static <T> Result<T> fail(long code, String msg) {
        return new Result<T>()
                .setCode(code)
                .setMsg(msg);
    }

    public static <T> Result<T> fail(IErrorCode errorCode) {
        return new Result<T>()
                .setCode(errorCode.getCode())
                .setMsg(errorCode.getMsg());
    }

    public static <T> Result<T> fail(IErrorCode errorCode, String msg) {
        return new Result<T>()
                .setCode(errorCode.getCode())
                .setMsg(msg);
    }

    /**
     * 参数校验失败
     */
    public static <T> Result<T> validateFailed() {
        return Result.fail(SysCode.VALIDATE_FAILED);
    }

    /**
     * 参数校验失败
     */
    public static <T> Result<T> validateFailed(String msg) {
        return new Result<T>()
                .setCode(SysCode.VALIDATE_FAILED.getCode())
                .setMsg(msg);
    }

    public static <T> Result<T> ok() {
        return new Result<T>()
                .setCode(SysCode.SUCCESS.getCode())
                .setMsg(SysCode.SUCCESS.getMsg())
                .setSuccess(true);
    }

    public static <T> Result<T> okMsg(String msg) {
        return new Result<T>()
                .setCode(SysCode.SUCCESS.getCode())
                .setMsg(msg)
                .setSuccess(true);
    }

    public static <T> Result<T> ok(T t) {
        return new Result<T>()
                .setCode(SysCode.SUCCESS.getCode())
                .setMsg(SysCode.SUCCESS.getMsg())
                .setSuccess(true)
                .setData(t);
    }

    public static <T> Result<T> ok(T t, String msg) {
        return new Result<T>()
                .setCode(SysCode.SUCCESS.getCode())
                .setMsg(msg)
                .setSuccess(true)
                .setData(t);
    }
}
