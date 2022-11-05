package com.zoyo.common.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mxx
 * @Description:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AudioSyndContent implements Serializable {

    private static final long serialVersionUID = -7111814564401162808L;

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
     * 详述
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
