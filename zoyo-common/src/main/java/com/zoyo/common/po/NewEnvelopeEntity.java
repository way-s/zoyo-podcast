package com.zoyo.common.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: mxx
 * @Description:
 */
@Data
@Accessors(chain = true)
public class NewEnvelopeEntity implements Serializable {

    private static final long serialVersionUID = 6745836268632787031L;

    /**
     * id
     */
    private Long id;

    /**
     * 节目id
     */
    private String subId;

    /**
     * 完成收听（0 未完成，1已经完成）
     */
    private Integer finish;

    /**
     * 收藏（0 未收藏，1已收藏）
     */
    private Integer collect;

    /**
     * 在播放队列（0 存在，1不存在）
     */
    private Integer inQueue;

    /**
     * 播客节目名
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 播客节目图片
     */
    private String image;

    /**
     * 作者
     */
    private String author;

    /**
     * 音频类型
     */
    private String audioType;

    /**
     * 描述
     */
    private String description;

    /**
     * 描述类型
     */
    private String descriptionType;

    /**
     * 插话
     */
    private String episode;

    /**
     * 发布时间
     */
    private LocalDateTime pubDate;

    /**
     * 音频时长
     */
    private String duration;

    /**
     * 详述
     */
    private String explicit;

    /**
     * 音频地址
     */
    private String audioUrl;

    /**
     * 资源链接
     */
    private String resourceLink;

    /**
     * 删除（0 存在，1 删除）
     */
    private Integer deleted;

}
