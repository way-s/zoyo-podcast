package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.po.AudioItemHouseEntity;
import com.zoyo.web.mapper.AudioItemHouseMapper;
import com.zoyo.web.service.IAudioItemHouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 每期节目信息表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AudioItemHouseServiceImpl extends ServiceImpl<AudioItemHouseMapper, AudioItemHouseEntity> implements IAudioItemHouseService {

    @Resource
    private AudioItemHouseMapper itemHouseMapper;

    //    @Async(value = "tPool1")
    @Override
    public Future<Integer> updatePodCastItems(List<AudioItemHouseEntity> entities) {
        try {
            return new AsyncResult<>(itemHouseMapper.insertBatch(entities));
        } catch (Exception e) {
            log.error("updatePodCastItems方法 异常:{}", e.getMessage());
            e.printStackTrace();
        }
        return new AsyncResult<>(0);
    }

    @Override
    public int updatePodCastItems2(List<AudioItemHouseEntity> entities) {
        return itemHouseMapper.insertBatch(entities);
    }
}
