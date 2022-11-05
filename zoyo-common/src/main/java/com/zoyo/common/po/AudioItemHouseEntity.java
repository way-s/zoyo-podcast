package com.zoyo.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 每期节目信息表
 * </p>
 *
 * @author mxx
 */
@Data
@Accessors(chain = true)
@TableName("zy_audio_item_house")
public class AudioItemHouseEntity implements Serializable {

    private static final long serialVersionUID = -964937771956154711L;

    /**
     * id
     */
    private Long id;

    /**
     * rss id，rss基本信息id
     */
    private String feedId;

    /**
     * 每期节目id
     */
    private String programId;

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
     * 状态（0正常，1停用）
     */
    private Integer status;

    /**
     * 删除（0 存在，1 删除）
     */
    private Integer deleted;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}