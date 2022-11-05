package com.zoyo.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 播客订阅表
 * </p>
 *
 * @author mxx
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("zy_audio_subscribe")
public class AudioSubscribeEntity implements Serializable {

    private static final long serialVersionUID = 6366886387361374477L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * rss id，rss基本信息id
     */
    private String feedId;

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
