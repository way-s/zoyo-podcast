package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.po.AudioRssEntity;
import com.zoyo.web.mapper.AudioRssMapper;
import com.zoyo.web.service.IAudioRssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 播客rss表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AudioRssServiceImpl extends ServiceImpl<AudioRssMapper, AudioRssEntity> implements IAudioRssService {

}
