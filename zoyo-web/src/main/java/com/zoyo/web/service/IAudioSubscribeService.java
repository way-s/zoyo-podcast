package com.zoyo.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.po.AudioSubscribeEntity;
import com.zoyo.common.vo.PodcastSummary;
import com.zoyo.common.vo.Result;

/**
 * <p>
 * 播客订阅表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IAudioSubscribeService extends IService<AudioSubscribeEntity> {
    /**
     * 解析 rss 链接
     *
     * @param rssUrl rss 链接
     * @return podcast 简介
     */
    Result<Object> analysisPodcastRssLink(String rssUrl);

    /**
     * 订阅
     *
     * @param podcastSummary 播客摘要
     * @return result
     */
    Result<Object> subscribePodcast(PodcastSummary podcastSummary);

    /**
     * 删除订阅的播客
     *
     * @param id 播客 feedId
     * @return result
     */
    Result<Object> deleteSubscribePodcast(String id);

    /**
     * 获取用户订阅播客
     *
     * @return result
     */
    Result<Object> queryUserSubscribe();
}
