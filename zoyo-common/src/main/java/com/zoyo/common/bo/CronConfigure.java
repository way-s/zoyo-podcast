package com.zoyo.common.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: mxx
 * @Description: 定时计划配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "scheduled")
public class CronConfigure {
    /**
     * 时区
     */
    private String zone;
    /**
     * 延迟2秒
     */
    private long initialDelay2s;
    /**
     * 延迟3秒
     */
    private long initialDelay3s;

    private long fixedDelay6Min;

    private long fixedDelay10Min;
    /**
     * 间隔6分钟
     */
    private String sixMinApart;
    /**
     * 间隔9分钟
     */
    private String nineMinApart;
    /**
     * 间隔 10分钟
     */
    private String tenMinApart;

}
