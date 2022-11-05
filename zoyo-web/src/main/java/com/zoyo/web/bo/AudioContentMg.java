package com.zoyo.web.bo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mxx
 * @Description: mongodb 数据集
 */
@Data
@Document(collation = "AudioContent")
public class AudioContentMg implements Serializable {

    private static final long serialVersionUID = 6076185231738087752L;

    @Indexed(unique = true)
    private String feedId;

    /**
     * 标题
     */
    private String title;
    /**
     * 资源链接
     */
    private String resourceLink;
    /**
     * 作者
     */
    private String author;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 描述
     */
    private String description;
    /**
     * 描述类型
     */
    private String descriptionType;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 音频类型
     */
    private String audioType;
    /**
     * 音频路径
     */
    private String audioUrl;
    /**
     * 发布时间
     */
    private Date pubDate;
    /**
     * 详述的
     */
    private String explicit;
    /**
     * 音频时长
     */
    private String duration;
    /**
     * 插话
     */
    private String episode;

}
