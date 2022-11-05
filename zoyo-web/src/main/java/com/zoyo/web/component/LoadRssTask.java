package com.zoyo.web.component;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rometools.rome.feed.synd.SyndFeed;
import com.zoyo.common.bo.AudioFeedBasicInfo;
import com.zoyo.common.bo.AudioSubscribeBo;
import com.zoyo.common.bo.AudioSyndContent;
import com.zoyo.common.bo.AudioSyndFeed;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.po.AudioItemHouseEntity;
import com.zoyo.common.po.AudioRssEntity;
import com.zoyo.common.po.AudioSubscribeEntity;
import com.zoyo.common.po.UserSubscribe;
import com.zoyo.web.mapper.AudioRssMapper;
import com.zoyo.web.mapper.AudioSubscribeMapper;
import com.zoyo.web.service.IAudioInboxService;
import com.zoyo.web.service.IAudioItemHouseService;
import com.zoyo.web.util.EncryptionUtil;
import com.zoyo.web.util.ParsePodcastRssUtil;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mxx
 * @Description:
 */
@Slf4j
@Component
public class LoadRssTask {

    /**
     * 定时（每15分钟执行一次）加载或更新
     * 1. 用户订阅feedIds
     * 2. rss摘要
     * 3. rss最近100期节目的更新
     */
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AudioSubscribeMapper subscribeMapper;

    @Resource
    private AudioRssMapper audioRssMapper;

    @Resource
    private IAudioItemHouseService itemHouseService;

    @Resource
    private IAudioInboxService inboxService;

    @Resource
    private ParsePodcastRssUtil podcastRssUtil;


    /**
     * 将用户订阅加载到 redis缓存中
     * 每隔10分钟完成一次更新
     * 0/10 从0分钟开始，每10分钟执行一次
     */
//    @PostConstruct
    @Async(value = "tPool1")
//    @Scheduled(initialDelay = 1000 * 2, fixedDelay = 1000 * 60 * 9, zone = "GMT-8:00")
    public void loadSubscribeIntoRedis() {
        // 取数据库中所有的rss订阅
        List<AudioRssEntity> audioSubscribeEntities = audioRssMapper.selectList(new LambdaQueryWrapper<AudioRssEntity>()
                .select(AudioRssEntity::getFeedId, AudioRssEntity::getOwnerName,
                        AudioRssEntity::getHref, AudioRssEntity::getTitle,
                        AudioRssEntity::getImage)
                .groupBy(AudioRssEntity::getFeedId)
                .eq(AudioRssEntity::getDeleted, 0)
        );
        List<AudioSubscribeBo> audioSubscribeBos = Convert.toList(AudioSubscribeBo.class,
                audioSubscribeEntities);
        redisUtil.set(RedisConstant.SUBSCRIBE_RSS, audioSubscribeBos);
        log.info("完成订阅更新，时间为 :{}", DateUtil.now());
    }

    /**
     * 解析订阅添加到 redis数据库中
     */
//    @Async(value = "tPool1")
//    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 60 * 10, zone = "GMT-8:00")
    public void loadPodcastIntoRedis() {
        try {
            Object obj = redisUtil.get(RedisConstant.SUBSCRIBE_RSS);
            List<AudioSubscribeBo> list = Convert.toList(AudioSubscribeBo.class, obj);
            log.info("Subscribe Podcast count :{}", list.size());

            for (AudioSubscribeBo subscribe : list) {
                SyndFeed feed = podcastRssUtil.buildUrlFeed(subscribe.getHref());
                if (feed == null) {
                    log.error("节目名为：{}，rss地址为：{}，feedId 为 :{}，解析异常",
                            subscribe.getTitle(), subscribe.getHref(), subscribe.getFeedId());
                    // rss 解析异常，之后根据该数据库通知订阅者
                    redisUtil.zAdd(RedisConstant.PODCAST_RSS_ERROR, subscribe.getFeedId()
                            + "," + subscribe.getTitle(), 1);
                    continue;
                }
                // 解析 rss
                AudioFeedBasicInfo basicInfo = podcastRssUtil.getFeedBasicInfo(feed);
                AudioSyndFeed rssFeed = podcastRssUtil.getSyndFeed(feed, basicInfo);
                // 播客摘要放到 redis中
                redisUtil.hSet(RedisConstant.PODCAST_SUMMARY, subscribe.getFeedId()
                        , rssFeed.getPodcastInfo());

                List<AudioSyndContent> items = rssFeed.getItems();
                for (AudioSyndContent content : items) {
                    // 播客的内容
                    redisUtil.hSet(RedisConstant.PODCAST_CONTENT + subscribe.getFeedId(),
                            EncryptionUtil.encrypt(
                                    String.valueOf(content.getPubDate().getTime())),
                            JSON.toJSON(content));
                }
            }
        } catch (Exception e) {
            log.error("loadPodcastIntoRedis error :{}", e.getMessage());
            e.printStackTrace();
        }
//        itemInfoWriteInWarehouse();
    }

    /**
     * 获取所有用户订阅
     */
    @Async(value = "tPool1")
//    @Scheduled(initialDelay = 1000 * 2, fixedDelay = 1000 * 60 * 10, zone = "GMT-8:00")
    public void updateUserSubscribeInfo() {
        // 获取所有用户订阅
        List<AudioSubscribeEntity> userList = subscribeMapper.selectList(new LambdaQueryWrapper<AudioSubscribeEntity>()
                .select(AudioSubscribeEntity::getUserId)
                .eq(AudioSubscribeEntity::getDeleted, 0)
                .groupBy(AudioSubscribeEntity::getUserId)
        );
        // 获取每个用户的订阅数据
        List<UserSubscribe> feedIds = subscribeMapper.getUsersSubscribeInfo(userList);
        log.info("infos.size :{}", feedIds.size());
        setUserSubscribeInfoToRedis(feedIds);
    }

    /**
     * 更新单个用户的订阅
     */
    @Async(value = "tPool1")
    public void updateSubscribeInfoById(Long userId) {
        // 获取用户的订阅数据
        List<UserSubscribe> feedIds = subscribeMapper.getSingleUserSubscribeInfo(userId);
        log.info("infos.size :{}", feedIds.size());
        setUserSubscribeInfoToRedis(feedIds);
    }

    /**
     * 将解析的每期节目信息写入数据库
     */
//    @Async(value = "tPool1")
//    @Scheduled(initialDelay = 1000 * 30, fixedDelay = 1000 * 60 * 10, zone = "GMT-8:00")
    public void itemInfoWriteInWarehouse() {
        Set<String> keys = redisUtil.getListKey(RedisConstant.PODCAST_CONTENT, 1000);
        // rss key值
//        List<String> listKey = Convert.toList(String.class, keys);
        log.info("keys size :{}", keys.size());
        for (String rssKey : keys) {
            // rss item key值
            List<Map.Entry<Object, Object>> itemListKeyAndValue = redisUtil.getListKeyAndValue(rssKey, "*");

            rssKey = rssKey.replace(RedisConstant.PODCAST_CONTENT, "");
            List<AudioItemHouseEntity> entities = convertItemEntity(itemListKeyAndValue, rssKey);
            // 将item内入批量写入数据库
//            CompletableFuture<Integer> future =
//            Future<Integer> future = itemHouseService.updatePodCastItems(entities);
//            int i = itemHouseService.updatePodCastItems2(entities);
            int i = itemHouseService.updatePodCastItems2(entities);
            // 将主键添加redis中
            for (AudioItemHouseEntity entity : entities) {
                redisUtil.zAdd(RedisConstant.PODCAST_UPDATE_CONTENT + entity.getFeedId()
                        , entity.getId(), 1);
            }

//            log.info("feed :{} ，更新了:{}条数据", rssKey, i);
//            try {
//                log.info("feed :{} ，更新了:{}条数据", rssKey, future.get());
//            } catch (ExecutionException | InterruptedException e) {
//                log.error("future.get() 异常，原因是:{}", e.getMessage());
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 更新用户收件箱
     */
    public void updateUserInbox() {
        // todo 查询线用户的订阅
        inboxService.updateUserInBox();
    }

    /**
     * 将用户的订阅feedId 添加到redis中
     *
     * @param feedIds feedIds
     */
    private void setUserSubscribeInfoToRedis(List<UserSubscribe> feedIds) {
        for (UserSubscribe subscribe : feedIds) {
            redisUtil.hSet(RedisConstant.USER_SUBSCRIBE, subscribe.getUserId().toString(),
                    subscribe.getLists());
        }
    }

    /**
     * 将map转为AudioItemHouseEntity实体对象
     *
     * @param listKeyAndValue redis hash keyAndValue
     * @param rssKey          rssKey
     * @return list
     */
    private List<AudioItemHouseEntity> convertItemEntity(List<Map.Entry<Object, Object>> listKeyAndValue, String rssKey) {
        List<AudioItemHouseEntity> list = new ArrayList<>(16);
        // 将map转为实体类
        for (Map.Entry<Object, Object> map : listKeyAndValue) {
            AudioItemHouseEntity entity = Convert.convert(AudioItemHouseEntity.class, map.getValue());
            // 可能出现 author为null
            String author = entity.getAuthor();
            if (author == null) {
                Object obj = redisUtil.hGet(RedisConstant.PODCAST_SUMMARY, map.getKey().toString());
                if (obj != null) {
                    AudioFeedBasicInfo basicInfo = Convert.convert(AudioFeedBasicInfo.class, obj);
                    entity.setAuthor(basicInfo.getAuthor());
                }
            }
            entity.setFeedId(rssKey).setProgramId(map.getKey().toString());
            list.add(entity);
        }
        return list;
    }

}
