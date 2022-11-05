package com.zoyo.common.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AudioFeedBasicInfo implements Serializable {

    private static final long serialVersionUID = -5402498900929117843L;

    /**
     * 标题
     */
    private String title;
    /**
     * 发行方链接
     */
    private String issuerLink;
    /**
     * 语言
     */
    private String language;
    /**
     * 描述
     */
    private String description;
    /**
     * 描述类型
     */
    private String descriptionType;
    /**
     * 类型
     */
    private String feedType;
    /**
     * 版权方
     */
    private String copyright;
    /**
     * rss链接
     */
    private String rssLink;
    /**
     * itunes标题
     */
    private String itunesTitle;
    /**
     * 作者
     */
    private String author;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 拥有者
     */
    private String owner;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 类型
     */
    private String type;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 详述
     */
    private String explicit;
    /**
     * 类别
     */
    private String category;

}
