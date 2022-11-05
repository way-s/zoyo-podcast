package com.zoyo.common.vo;

/**
 * @Author: mxx
 * @Description:
 */
public interface IErrorCode {

    /**
     * 状态码
     *
     * @return code
     */
    long getCode();

    /**
     * 返回信息
     *
     * @return msg
     */
    String getMsg();
}
