package com.zoyo.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PodcastSummary implements Serializable {

    private static final long serialVersionUID = -77763093441805156L;
    /**
     * rss id，rss基本信息id
     */
    private String feedId;

    /**
     * rss 地址链接
     */
    private String href;

    /**
     * 播客节目名
     */
    private String title;

    /**
     * 播客节目logo
     */
    private String image;

    /**
     * 播客作者
     */
    private String ownerName;

}
