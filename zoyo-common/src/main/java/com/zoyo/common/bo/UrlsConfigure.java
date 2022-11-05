package com.zoyo.common.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: mxx
 * @Description: 获取接口地址
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "secure.roster")
public class UrlsConfigure {
    /**
     * 管理员权限访问路径
     */
    private List<String> adminUrls;

    /**
     * 白名单
     */
    private List<String> ignoredUrls;

}
