package com.zoyo.common.dto;

import lombok.Data;

/**
 * @Author: mxx
 * @Description:
 */
@Data
public class MessageInfo {

    /**
     * 源客户端id
     */
    private String sourceClientId;
    /**
     * 目标客户端id
     */
    private String targetClientId;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息内容
     */
    private String msg;

}
