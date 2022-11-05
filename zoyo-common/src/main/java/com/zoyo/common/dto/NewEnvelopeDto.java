package com.zoyo.common.dto;

import com.zoyo.common.po.NewEnvelopeEntity;
import com.zoyo.common.vo.NewEnvelopeVo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: mxx
 * @Description:
 */
@Data
public class NewEnvelopeDto implements Serializable {

    private static final long serialVersionUID = 3047989925981055020L;

    /**
     * id
     */
    private Long id;

    /**
     * 节目id
     */
//    private String subId;

    /**
     * 完成收听（0 未完成，1已经完成）
     */
    private Boolean finish;

    /**
     * 收藏（0 未收藏，1已收藏）
     */
    private Boolean collect;

    /**
     * 在播放队列（0 存在，1不存在）
     */
    private Boolean inQueue;

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
     * po转dto
     */
    public NewEnvelopeDto toNewEnvelopeDto(NewEnvelopeEntity entity) {
        this.setId(entity.getId());
        this.setTitle(entity.getTitle());
        this.setImage(entity.getImage());
        this.setSubtitle(entity.getSubtitle());
//        this.setSubId(entity.getSubId());
        this.setAudioType(entity.getAudioType());
        this.setAudioUrl(entity.getAudioUrl());
        this.setAuthor(entity.getAuthor());
        this.setCollect(entity.getCollect() != null && entity.getCollect() != 0);
        this.setFinish(entity.getFinish() != null && entity.getFinish() != 0);
        this.setInQueue(entity.getInQueue() != null && entity.getInQueue() != 0);
        this.setDescription(entity.getDescription());
        this.setDescriptionType(entity.getDescriptionType());
        this.setPubDate(entity.getPubDate());
        this.setDuration(entity.getDuration());
        this.setEpisode(entity.getEpisode());
        this.setExplicit(entity.getExplicit());
        this.setResourceLink(entity.getResourceLink());
        return this;
    }

    /**
     * vo转dto
     */
    public NewEnvelopeEntity toNewEnvelopePo(NewEnvelopeVo entity) {
        NewEnvelopeEntity po = new NewEnvelopeEntity();
        po.setId(entity.getId());
        po.setTitle(entity.getTitle());
        po.setImage(entity.getImage());
        po.setSubtitle(entity.getSubtitle());
//        po.setSubId(entity.getSubId());
        po.setAudioType(entity.getAudioType());
        po.setAudioUrl(entity.getAudioUrl());
        po.setAuthor(entity.getAuthor());
        po.setFinish(entity.getFinish() ? 1 : 0);
        po.setFinish(entity.getFinish() ? 1 : 0);
        po.setInQueue(entity.getInQueue() ? 1 : 0);
        po.setDescription(entity.getDescription());
        po.setDescriptionType(entity.getDescriptionType());
        po.setPubDate(entity.getPubDate());
        po.setDuration(entity.getDuration());
        po.setEpisode(entity.getEpisode());
        po.setExplicit(entity.getExplicit());
        po.setResourceLink(entity.getResourceLink());
        return po;
    }

}
