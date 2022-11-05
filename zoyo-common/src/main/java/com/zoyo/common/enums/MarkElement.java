package com.zoyo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: mxx
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum MarkElement {
    /**
     * feed xml 获取自定义标记信息常量
     */
    LINK("link", "链接"),
    AUTHOR("author", "作者"),
    SUMMARY("summary", "摘要"),
    OWNER("owner", "作者，拥有者"),
    KEYWORDS("keywords", "关键词"),
    IMAGE("image", "图片"),
    EXPLICIT("explicit", "明确的"),
    CATEGORY("category", "种类"),
    SUBTITLE("subtitle", "副标题"),
    DURATION("duration", "时长"),
    HREF("href", "链接"),
    TYPE("type", "类型");

    private final String val;
    private final String description;

}
