package com.zoyo.web.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.constant.SysConstant;
import com.zoyo.common.dto.NewEnvelopeDto;
import com.zoyo.common.po.AudioInboxEntity;
import com.zoyo.common.po.NewEnvelopeEntity;
import com.zoyo.common.vo.Result;
import com.zoyo.web.component.MqSender;
import com.zoyo.web.handler.SocketIoEventHandler;
import com.zoyo.web.mapper.AudioInboxMapper;
import com.zoyo.web.mapper.AudioPlaylistMapper;
import com.zoyo.web.service.IAudioInboxService;
import com.zoyo.web.util.CurrentSubject;
import com.zoyo.web.util.EntityConvertUtil;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 播客收件箱表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AudioInboxServiceImpl extends ServiceImpl<AudioInboxMapper, AudioInboxEntity> implements IAudioInboxService {

    @Resource
    private CurrentSubject currentSubject;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MqSender mqSender;

    @Resource
    private SocketIoEventHandler socketIoEventHandler;

    @Resource
    private AudioInboxMapper inboxMapper;

    @Resource
    private AudioPlaylistMapper playlistMapper;

    @Override
    public Result<Object> queryNewEnvelope() {
        Long userId = currentSubject.getUserId();
        List<NewEnvelopeEntity> entities = this.inboxMapper.selectNewEnvelopeByUserId(userId);
        List<NewEnvelopeDto> dto = EntityConvertUtil.toNewEnvelopeDto(entities);
        return Result.ok(dto);
    }

    @Override
    public void updateUserInBox() {

        // todo 查询线用户的订阅
//        String rssKey = "";
//        Set<Object> itemKeys = redisUtil.zRangeByScore(RedisConstant.PODCAST_UPDATE_CONTENT +
//                rssKey, 0, 5);
//        List<String> items = Convert.toList(String.class, itemKeys);
        // 获取在线用户
        Set<Object> objects = redisUtil.zRangeByScore(RedisConstant.SOCKET_ONLINE, 0, 5);

        List<String> onLineUsers = Convert.toList(String.class, objects);
        for (String userId : onLineUsers) {

            // 获取用户订阅的rss feedId
            Object obj = redisUtil.hGet(RedisConstant.USER_SUBSCRIBE, userId);
            List<String> userSubscribes = Convert.toList(String.class, obj);
            for (String feedId : userSubscribes) {
                //todo 批量查询找出，新消息的id，再将其放入用户的收件箱中
                Set<Object> set = redisUtil.zRangeByScore(RedisConstant.PODCAST_UPDATE_CONTENT + feedId, 0, 5);
                List<String> newItemId = Convert.toList(String.class, set);
                // 批量更新
//                int updateNum = inboxMapper.insertBatchByItemId(userId, newItemId);
                List<AudioInboxEntity> list = new ArrayList<>(16);
                newItemId.forEach(itemId -> {
                    AudioInboxEntity inboxEntity = new AudioInboxEntity()
                            // todo 修改 sub_id字段，改名称
                            .setUserId(Long.parseLong(userId)).setSubId(itemId);
                    list.add(inboxEntity);
                });
                if (list.size() > 0) {
                    int updateNum = inboxMapper.insertBatchByEntity(list);
                    // 使用mp测试
//                    this.saveBatch(list);
//                    log.info("rss:{}，更新了:{}条数据", feedId, list.size());
//                    int updateNum = list.size();
                    // 将更新数量放入redis中
                    // 判断redis中是否存在该订阅者的news数据
                    Boolean exist = redisUtil.hHasKey(RedisConstant.NEWS_NUMBER, userId);
                    if (exist) {
                        // 增加
                        redisUtil.hIncr(RedisConstant.NEWS_NUMBER, userId, (long) updateNum);
                    } else {
                        // 创建
                        redisUtil.hSet(RedisConstant.NEWS_NUMBER, userId, updateNum);
                    }
                }
            }
        }
    }


    /**
     * 模拟新数据
     *
     * @param userId userId
     */
    private void mockSendNews(String userId) {
        String num = "2";
        socketIoEventHandler.pushToOnlineUser(userId,
                SysConstant.WS_SINGLE_PASSAGE, num);
    }
}
