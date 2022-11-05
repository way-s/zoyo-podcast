package com.zoyo.common.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@Setter
public class AudioSubscribeBo  implements Serializable {

    private static final long serialVersionUID = 9000656856756160558L;

    /**
     * rss id，rss基本信息id
     */
    private String feedId;

    /**
     * rss 地址链接
     */
    private String href;

    /**
     * 播客节目名
     */
    private String title;

    /**
     * 播客节目logo地址
     */
    private String image;

    /**
     * 播客作者
     */
    private String ownerName;

}
