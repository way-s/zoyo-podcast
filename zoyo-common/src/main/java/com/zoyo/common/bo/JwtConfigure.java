package com.zoyo.common.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigure {

    private String tokenHeader;

    private String secret;

    private Long expiration;

    private String tokenHead;
}
