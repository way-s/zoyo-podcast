package com.zoyo.common.constant;

/**
 * @Author: mxx
 * @Description: redis 前缀常量
 */
public class RedisConstant {
    /**
     * 用户信息基本信息主键
     */
    public static final String FM_WEB_USER = "ZOYO_WEB:USER_KEY:";

    /**
     * 登录成功的token 有效时长
     */
    public static final long LOGIN_EXPIRATION = 60 * 60 * 6;

    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "ZOYO_WEB:TOKEN_KEY:";

    /**
     * socket缓存
     */
    public static final String SOCKET_ONLINE = "ZOYO_WEB:ON_LINE";

    /**
     * 播客订阅 rss
     */
    public static final String SUBSCRIBE_RSS = "ZOYO_WEB:SUBSCRIBE:RSS";

    /**
     * 用户的订阅
     */
    public static final String USER_SUBSCRIBE = "ZOYO_WEB:SUBSCRIBE:USER";

    /**
     * 播客订阅的摘要
     */
    public static final String PODCAST_SUMMARY = "ZOYO_WEB:SUBSCRIBE:PODCAST_SUMMARY";

    /**
     * 播客内容
     */
    public static final String PODCAST_CONTENT = "ZOYO_WEB:SUBSCRIBE:PODCAST_ITEMS:";
    /**
     * 更新的（每期）节目
     */
    public static final String PODCAST_UPDATE_CONTENT = "ZOYO_WEB:SUBSCRIBE:PODCAST_UPDATE_ITEMS:";

    /**
     * 播客 RSS解析错误
     */
    public static final String PODCAST_RSS_ERROR = "ZOYO_WEB:SUBSCRIBE:PODCAST_RSS_ERROR";

    /**
     * 用户新消息数量
     */
    public static final String NEWS_NUMBER = "ZOYO_WEB:SUBSCRIBE:PODCAST_NEWS";

}
