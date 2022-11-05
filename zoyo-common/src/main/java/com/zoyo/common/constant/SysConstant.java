package com.zoyo.common.constant;

/**
 * @Author: mxx
 * @Description: 通用常量
 */
public class SysConstant {
    /**
     * 账号，创建 token时的 key值
     */
    public static final String ACCOUNT = "account";
    /**
     * 账号，创建 token时的 key值
     */
    public static final String ID = "id";
    /**
     * 密码，创建 token时的 key值
     */
    public static final String PASSWORD = "password";
    /**
     * 分割符号
     */
    public static final String SPLIT = "-";
    /**
     * mq 队列名
     */
    public static final String TOPIC_QUEUE_NAME = "zy.web.queue";
    /**
     * socket io 消息单发事件名
     */
    public static final String WS_SINGLE_PASSAGE = "news";
    /**
     * rss 错误事件
     */
    public static final String WS_ERROR_PASSAGE = "errorNews";
    /**
     * 回复客户端通道
     */
    public static final String WS_REVERSION_PASSAGE = "reversion";
    /**
     * 回复客户端消息
     */
    public static final String WE_REVERSION_MSG = "true";
    /**
     * socket io 消息群发事件名
     */
    public static final String WS_NOTICE = "notice";


}
