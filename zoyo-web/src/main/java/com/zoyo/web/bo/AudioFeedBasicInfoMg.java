package com.zoyo.web.bo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description: mongodb 数据集
 */
@Data
@Document(collation = "AudioFeedBasicInfo")
public class AudioFeedBasicInfoMg implements Serializable {

    private static final long serialVersionUID = -6389056957077376647L;
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
     * 标题
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
     * 详述的
     */
    private String explicit;
    /**
     * 类别
     */
    private String category;

}
