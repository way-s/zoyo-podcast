package com.zoyo.web.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.zoyo.common.bo.*;
import com.zoyo.common.enums.MarkElement;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: mxx
 * @Description: 解析播客 rss地址的工具类
 */
@Slf4j
@Component
public class ParsePodcastRssUtil {

    /**
     * 解析 rss url
     *
     * @param rssUrl rss链接地址
     * @return SyndFeed实体对象
     */
    public SyndFeed buildUrlFeed(String rssUrl) {
        try {
            URL feedUrl = new URL(rssUrl);
            // 取RSS资源
            XmlReader reader = new XmlReader(feedUrl);
            // 将 rss 链接解析得到的数据转为 SyndFeed对象
            SyndFeedInput input = new SyndFeedInput();
            return input.build(reader);
        } catch (FeedException | IOException e) {
            log.error("buildUrlFeed 方法异常，原因为：{}", e.getMessage());
//            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取播客摘要
     *
     * @param feed feed 解析
     * @return rss 摘要
     */
    public AudioFeedBasicInfo getFeedBasicInfo(SyndFeed feed) {
        AudioFeedBasicInfo basicInfo = new AudioFeedBasicInfo();
        // 非标记基本信息
        String titleVal = feed.getTitleEx().getValue();
        String issuerLink = feed.getLink();
        String language = feed.getLanguage();
        String description = feed.getDescriptionEx().getValue();
        String descriptionType = feed.getDescriptionEx().getType();
        String feedType = feed.getFeedType();
        String copyright = feed.getCopyright();

        basicInfo.setTitle(StrUtil.isNotBlank(titleVal) ? titleVal.trim() : null)
                .setIssuerLink(StrUtil.isNotBlank(issuerLink) ? issuerLink.trim() : null)
                .setLanguage(StrUtil.isNotBlank(language) ? language.trim() : null)
                .setDescription(StrUtil.isNotBlank(description) ? description.trim() : null)
                .setDescriptionType(StrUtil.isNotBlank(descriptionType) ? descriptionType.trim() : null)
                .setFeedType(StrUtil.isNotBlank(feedType) ? feedType.trim() : null)
                .setCopyright(StrUtil.isNotBlank(copyright) ? copyright.trim() : null);

        return basicInfo;
    }

    /**
     * 获取解析的rss内容
     *
     * @param feed      feed 解析
     * @param basicInfo 播客摘要
     * @return AudioSyndFeed 解析的rss内容
     */
    public AudioSyndFeed getSyndFeed(SyndFeed feed, AudioFeedBasicInfo basicInfo) {
        AudioSyndFeed audioFeed = new AudioSyndFeed();
        // 基本标记元素
        FeedMarkElement markEl = getForeignMarkup(feed);
        if (markEl != null) {
            basicInfo.setRssLink(markEl.getRssLink())
                    .setItunesTitle(markEl.getItunesTitle())
                    .setAuthor(markEl.getAuthor())
                    .setSummary(markEl.getSummary())
                    .setOwner(markEl.getOwner())
                    .setKeywords(markEl.getKeywords())
                    .setType(markEl.getType())
                    .setImage(markEl.getImage())
                    .setExplicit(markEl.getExplicit())
                    .setCategory(markEl.getCategory());

            audioFeed.setPodcastInfo(basicInfo);
        }
        // 每期集合
        List<AudioSyndContent> items = getContentEntry(basicInfo, feed);
        if (items != null) {
            audioFeed.setItems(items);
        }

        return audioFeed;
    }

    /**
     * @param feed feed 解析
     * @return 基本标记元素
     */
    public FeedMarkElement getForeignMarkup(SyndFeed feed) {
        List<Element> foreignMarkup = feed.getForeignMarkup();
        FeedMarkElement markElement = new FeedMarkElement();
        try {
            for (Element element : foreignMarkup) {
                // 元素名
                String elName = element.getName();
                // link
                if (elName.equals(MarkElement.LINK.getVal())) {
                    List<Attribute> attributes = element.getAttributes();
                    if (attributes.size() > 0) {
                        for (Attribute attribute : attributes) {
                            if (attribute.getName().equals(MarkElement.HREF.getVal())) {
                                String linkVal = attribute.getValue();
                                markElement.setRssLink(linkVal != null ? linkVal.trim() : null);
                            }
                        }
                    }
                }
                // author
                else if (elName.equals(MarkElement.AUTHOR.getVal())) {
                    List<Content> content = element.getContent();
                    String authorVal = element.getTextTrim();
                    if (authorVal != null) {
                        markElement.setAuthor(authorVal);
                    } else if (content.size() > 0) {
                        String author = content.get(content.size() - 1).getValue();
                        markElement.setAuthor(author != null ? author.trim() : null);
                    }
                }
                // summary
                else if (elName.equals(MarkElement.SUMMARY.getVal())) {
                    List<Content> content = element.getContent();
                    if (content.size() > 0) {
                        String summary = content.get(content.size() - 1).getValue();
                        markElement.setSummary(summary != null ? summary.trim() : null);
                    }
                }
                // image
                else if (elName.equals(MarkElement.IMAGE.getVal())) {
                    List<Attribute> attributes = element.getAttributes();
                    if (attributes.size() > 0) {
                        for (Attribute attribute : attributes) {
                            if (attribute.getName().equals(MarkElement.HREF.getVal())) {
                                String imgHref = attribute.getValue();
                                markElement.setImage(imgHref != null ? imgHref.trim() : null);
                            }
                        }
                    }
                }
                // owner
                else if (elName.equals(MarkElement.OWNER.getVal())) {
                    List<Content> content = element.getContent();
                    if (content.size() > 0) {
                        List<Content> ownerList = Convert.toList(Content.class, content.get(1));
                        String owner = ownerList.get(0).getValue();
                        markElement.setOwner(owner != null ? owner.trim() : null);
                    }
                }
                // keywords
                else if (elName.equals(MarkElement.KEYWORDS.getVal())) {
                    List<Content> content = element.getContent();
                    if (content.size() > 0) {
                        String keywords = content.get(content.size() - 1).getValue();
                        markElement.setKeywords(keywords != null ? keywords.trim() : null);
                    }
                }
                // category
                else if (elName.equals(MarkElement.CATEGORY.getVal())) {
                    List<Attribute> attributes = element.getAttributes();
                    if (attributes.size() > 0) {
                        String category = attributes.get(attributes.size() - 1).getValue();
                        markElement.setCategory(category != null ? category.trim() : null);
                    }
                }
                // type
                else if (elName.equals(MarkElement.TYPE.getVal())) {
                    String typeVal = element.getTextTrim();
                    List<Content> content = element.getContent();
                    if (typeVal != null) {
                        markElement.setType(typeVal);
                    } else if (content.size() > 0) {
                        String type = content.get(content.size() - 1).getValue();
                        markElement.setType(type != null ? type.trim() : null);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getForeignMarkup()方法 获取基本标记元素异常，原因是" + e.getMessage());
            e.printStackTrace();
        }
        return markElement;
    }

    /**
     * 获取每期音频内容集合
     * 只取得 50期的数据
     *
     * @param feed 源
     * @return 内容集合
     */
    public List<AudioSyndContent> getContentEntry(AudioFeedBasicInfo basicInfo, SyndFeed feed) {
        List<SyndEntry> entries = feed.getEntries();
        List<AudioSyndContent> list = new ArrayList<>();
        try {
            int size = entries.size();
            log.info("节目名为：{}，共发现{}期", basicInfo.getTitle(), size);
            // 1. 只添加 100期的数据
            for (int i = 0; i < size; i++) {
                if (i > 9) {
                    break;
                }
                SyndEntry entry = entries.get(i);
                // 装载实体
                AudioSyndContent audioContent = new AudioSyndContent();

                String descriptionType = entry.getDescription() != null
                        ? entry.getDescription().getType().trim() : null;
                String descriptionVal = entry.getDescription() != null
                        ? entry.getDescription().getValue().trim() : null;
                String resourceLink = entry.getLink() != null
                        ? entry.getLink().trim() : null;
                String audioType = entry.getEnclosures().get(0) != null
                        ? entry.getEnclosures().get(0).getType().trim() : null;
                String audioUrl = entry.getEnclosures().get(0) != null
                        ? entry.getEnclosures().get(0).getUrl().trim() : null;
                Date publishedDate = entry.getPublishedDate()
                        != null ? entry.getPublishedDate() : null;
                String title = entry.getTitle() != null
                        ? entry.getTitle().trim() : null;

                audioContent.setDescriptionType(descriptionType)
                        .setDescription(descriptionVal)
                        .setResourceLink(resourceLink)
                        .setAudioType(audioType)
                        .setAudioUrl(audioUrl)
                        .setPubDate(publishedDate)
                        .setTitle(title);

                // 获取外围标记元素内容
                AudioContentMarkEl markEl = getContentMarkup(entry);
                String author = markEl.getAuthor();
                String subtitle = markEl.getSubtitle();
                String image = markEl.getImage();
                String duration = markEl.getDuration();

                audioContent.setAuthor(author != null ? author.trim() : null)
                        .setSubtitle(subtitle != null ? subtitle.trim() : null)
                        .setImage(image != null ? image.trim() : null)
                        .setDuration(duration != null ? duration.trim() : null);

                list.add(audioContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getContentEntry()方法，获取每期音频内容集合异常，原因是：" + e.getMessage());
        }
        return list;
    }


    /**
     * 获取 每期音频内容外围标记元素内容
     *
     * @param entry 每期音频内容
     * @return markEl 映射
     */
    private AudioContentMarkEl getContentMarkup(SyndEntry entry) {
        List<Element> markup = entry.getForeignMarkup();
        AudioContentMarkEl markEl = new AudioContentMarkEl();
        try {
            for (Element element : markup) {
                // 元素名
                String elName = element.getName();
                // author 作者
                if (elName.equals(MarkElement.AUTHOR.getVal())) {
                    List<Content> content = element.getContent();
                    String authorVal = element.getTextTrim();
                    if (authorVal != null) {
                        markEl.setAuthor(authorVal);
                    } else if (content.size() > 0) {
                        String author = content.get(0).getValue();
                        markEl.setAuthor(author);
                    }
                }
                // subtitle 副标题
                else if (elName.equals(MarkElement.SUBTITLE.getVal())) {
                    List<Content> content = element.getContent();
                    String subtitleVal = element.getTextTrim();
                    if (subtitleVal != null) {
                        markEl.setSubtitle(subtitleVal);
                    } else if (content.size() > 0) {
                        String subtitle = content.get(0).getValue();
                        markEl.setSubtitle(subtitle);
                    }
                }
                // image 图片
                else if (elName.equals(MarkElement.IMAGE.getVal())) {
                    List<Attribute> attributes = element.getAttributes();
                    if (attributes.size() > 0) {
                        for (Attribute attribute : attributes) {
                            if (attribute.getName().equals(MarkElement.HREF.getVal())) {
                                String imgHref = attribute.getValue();
                                markEl.setImage(imgHref);
                            }
                        }
                    }
                }
                // duration 时长
                else if (elName.equals(MarkElement.DURATION.getVal())) {
                    List<Content> content = element.getContent();
                    String durationVal = element.getTextTrim();
                    if (durationVal != null) {
                        String duration = convertDuration(durationVal);
                        markEl.setDuration(duration);
                    } else if (content.size() > 0) {
                        String duration = content.get(0).getValue();
                        String durationCov = convertDuration(duration);
                        markEl.setDuration(durationCov);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getContentMarkup()方法，获取每期音频内容外围标记元素内容异常，原因是：" + e.getMessage());
        }
        return markEl;
    }

    /**
     * 音频时长的转换
     *
     * @param duration 时长的转换
     * @return m:s or h:m:s
     */
    private String convertDuration(String duration) {
        if (duration.contains(":")) {
            return duration;
        }
        int time = Integer.parseInt(duration);
        String result;
        // 不到一分钟
        if (time < 60) {
            result = "00:" + time;
        }
        // 一小时以内
        else if (time > 60 && time < 3600) {
            int minute = time / 60;
            int second = time % 60;
            result = minute + ":" + second;
        }
        // 一小时以上
        else {
            int hour = time / 3600;
            int minute = (time % 3600) / 60;
            int second = (time % 3600) % 60;
            result = hour + ":" + minute + ":" + second;
        }
        return result;
    }

}
