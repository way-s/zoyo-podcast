package com.zoyo.web.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.zoyo.common.bo.AudioFeedBasicInfo;
import com.zoyo.common.bo.FeedMarkElement;
import com.zoyo.common.constant.SysConstant;
import com.zoyo.common.exception.Asserts;
import com.zoyo.common.po.AudioRssEntity;
import com.zoyo.common.po.AudioSubscribeEntity;
import com.zoyo.common.vo.PodcastSummary;
import com.zoyo.common.vo.Result;
import com.zoyo.web.component.LoadRssTask;
import com.zoyo.web.mapper.AudioRssMapper;
import com.zoyo.web.mapper.AudioSubscribeMapper;
import com.zoyo.web.service.IAudioSubscribeService;
import com.zoyo.web.util.CurrentSubject;
import com.zoyo.web.util.ParsePodcastRssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 播客订阅表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AudioSubscribeServiceImpl extends ServiceImpl<AudioSubscribeMapper, AudioSubscribeEntity> implements IAudioSubscribeService {

    @Resource
    private CurrentSubject currentSubject;

    @Resource
    private ParsePodcastRssUtil rssUtil;

    @Resource
    private AudioRssMapper audioRssMapper;

    @Resource
    private LoadRssTask rssTask;

    @Override
    public Result<Object> analysisPodcastRssLink(String rssUrl) {
        log.info("开始解析 rss地址 : {}", rssUrl);
        rssUrl = rssUrl.replace("\"", "");
        SyndFeed feed = rssUtil.buildUrlFeed(rssUrl);
        if (feed == null) {
            Asserts.fail("无法识别该链接");
        }
        AudioFeedBasicInfo basicInfo = rssUtil.getFeedBasicInfo(feed);
        FeedMarkElement markEl = rssUtil.getForeignMarkup(feed);

        List<PodcastSummary> list = new ArrayList<>();
        PodcastSummary summary = new PodcastSummary()
                .setTitle(StrUtil.isNotBlank(basicInfo.getTitle()) ? basicInfo.getTitle() : "未知播客")
                // 生成临时的 feedId
                .setFeedId(IdUtil.randomUUID())
                .setImage(markEl.getImage())
                .setHref(markEl.getRssLink())
                .setOwnerName(markEl.getOwner());
        list.add(summary);
        log.info("解析 rss地址结束，该播客节目名为 {}", basicInfo.getTitle());
        return Result.ok(list);
    }

    @Override
    public Result<Object> subscribePodcast(PodcastSummary podcastSummary) {
        Long userId = this.currentSubject.getUserInfo().getId();
        String userAccount = this.currentSubject.getUserAccount();

        String feedId = podcastSummary.getFeedId();
        String href = podcastSummary.getHref();
        String title = podcastSummary.getTitle();
        String image = podcastSummary.getImage();
        String ownerName = podcastSummary.getOwnerName();

        AudioSubscribeEntity one = this.getOne(new LambdaQueryWrapper<AudioSubscribeEntity>()
                .select(AudioSubscribeEntity::getId)
                .eq(AudioSubscribeEntity::getUserId, userId)
                .eq(AudioSubscribeEntity::getFeedId, feedId)
        );
        // 判断是否已经添加
        if (one != null) {
            Asserts.fail("已成功订阅");
        }
        // 先判断是否已经有用户添加过，存在则使用相同的feedId，否则新建uuid作为feedId
        // TODO 先查redis，再查数据库 2022-7-11
        AudioRssEntity existFeedId = audioRssMapper.selectOne(new LambdaQueryWrapper<AudioRssEntity>()
                .select(AudioRssEntity::getFeedId)
                .eq(AudioRssEntity::getOwnerName, ownerName)
                .eq(AudioRssEntity::getHref, href)
        );

        if (existFeedId != null) {
            feedId = existFeedId.getFeedId();
        }
        // 如果使用的是临时的 uuid，使用新的简化的uuid
        if (feedId.contains(SysConstant.SPLIT)) {
            feedId = IdUtil.simpleUUID();
        }
        // 新rss
        if (existFeedId == null) {
            AudioRssEntity newRss = new AudioRssEntity()
                    .setImage(StrUtil.isNotBlank(image) ? image : null)
                    .setFeedId(feedId)
                    .setHref(href)
                    .setTitle(title)
                    .setOwnerName(ownerName);
            this.audioRssMapper.insert(newRss);
        }

        AudioSubscribeEntity entity = new AudioSubscribeEntity()
                .setUserId(userId)
                .setFeedId(feedId);
        this.save(entity);
        log.info("账号为 {}，添加订阅 feedId: {}", userAccount, feedId);
        // 更新 redis
        rssTask.updateSubscribeInfoById(userId);
        return Result.ok();
    }

    @Override
    public Result<Object> deleteSubscribePodcast(String feedId) {
        Long userId = this.currentSubject.getUserInfo().getId();
        String userAccount = this.currentSubject.getUserAccount();
        log.info("账号为 {}，删除订阅 feedId: {}", userAccount, feedId);
        this.remove(new LambdaQueryWrapper<AudioSubscribeEntity>()
                .eq(AudioSubscribeEntity::getUserId, userId)
                .eq(AudioSubscribeEntity::getFeedId, feedId)
        );
        // 更新 redis
        rssTask.updateSubscribeInfoById(userId);
        return Result.ok();
    }

    @Override
    public Result<Object> queryUserSubscribe() {
        Long userId = this.currentSubject.getUserInfo().getId();
        List<PodcastSummary> summaryList = this.audioRssMapper
                .getPodcastSummaryByFeedId(userId);

        return Result.ok(summaryList);
    }


}
