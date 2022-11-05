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
public class AudioContentMarkEl implements Serializable {

    private static final long serialVersionUID = -7987895860169336744L;

    /**
     * 作者
     */
    private String author;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 音频时长
     */
    private String duration;
    /**
     * 插话
     */
    private String episode;

}
