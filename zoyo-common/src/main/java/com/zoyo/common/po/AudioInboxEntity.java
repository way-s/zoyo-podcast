package com.zoyo.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 播客收件箱表
 * </p>
 *
 * @author mxx
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("zy_audio_inbox")
public class AudioInboxEntity implements Serializable {

    private static final long serialVersionUID = 6637798562441102750L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

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
     * 状态（0 未读，1 已读）
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
