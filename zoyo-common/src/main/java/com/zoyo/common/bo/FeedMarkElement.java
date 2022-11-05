package com.zoyo.common.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: mxx
 * @Description: xml 文件中的标记元素映射
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedMarkElement implements Serializable {

    private static final long serialVersionUID = -6984762800426220565L;

    /**
     * 链接
     */
    private String rssLink;
    /**
     * 标题
     */
    private String itunesTitle;
    /**
     * 作者
     */
    private String author;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 拥有者
     */
    private String owner;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 类型
     */
    private String type;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 详述的
     */
    private String explicit;
    /**
     * 类别
     */
    private String category;

}
