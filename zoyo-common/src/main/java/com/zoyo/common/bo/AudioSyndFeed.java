package com.zoyo.common.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: mxx
 * @Description: xml 映射
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AudioSyndFeed implements Serializable {

    private static final long serialVersionUID = -8065643561085079114L;

    /**
     * 播客基本信息
     */
    private AudioFeedBasicInfo podcastInfo;
    /**
     * item 每期集合
     */
    private List<AudioSyndContent> items;

}
