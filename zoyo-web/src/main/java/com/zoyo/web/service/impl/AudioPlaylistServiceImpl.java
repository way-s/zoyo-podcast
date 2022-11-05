package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.dto.NewEnvelopeDto;
import com.zoyo.common.exception.Asserts;
import com.zoyo.common.po.AudioInboxEntity;
import com.zoyo.common.po.AudioPlaylistEntity;
import com.zoyo.common.po.NewEnvelopeEntity;
import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;
import com.zoyo.web.mapper.AudioInboxMapper;
import com.zoyo.web.mapper.AudioPlaylistMapper;
import com.zoyo.web.service.IAudioInboxService;
import com.zoyo.web.service.IAudioPlaylistService;
import com.zoyo.web.util.CurrentSubject;
import com.zoyo.web.util.EntityConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 播放列表表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AudioPlaylistServiceImpl extends ServiceImpl<AudioPlaylistMapper, AudioPlaylistEntity> implements IAudioPlaylistService {

    @Resource
    private CurrentSubject currentSubject;

    @Resource
    private AudioPlaylistMapper playlistMapper;

    @Resource
    private AudioInboxMapper inboxMapper;

    @Resource
    private IAudioInboxService inboxService;

    @Override
    public Result<Object> addEpisodeToPlayList(NewEnvelopeVo envelopeVo) {
        Long userId = currentSubject.getUserId();
        AudioPlaylistEntity one = this.getOne(new LambdaQueryWrapper<AudioPlaylistEntity>()
                .select(AudioPlaylistEntity::getId)
                .eq(AudioPlaylistEntity::getPartId, envelopeVo.getId().toString())
                .eq(AudioPlaylistEntity::getUserId, userId.toString())
        );
        if (one != null) {
            Asserts.fail("已添加到播放列表");
        }
        // 收件箱
        inboxService.update(new LambdaUpdateWrapper<AudioInboxEntity>()
                .set(AudioInboxEntity::getInQueue, 1)
                .eq(AudioInboxEntity::getId, envelopeVo.getId())
                .eq(AudioInboxEntity::getUserId, userId)
        );
        // 播放列表
        AudioPlaylistEntity playlistEntity = new AudioPlaylistEntity()
                .setUserId(userId)
                .setPartId(envelopeVo.getId().toString());
        this.playlistMapper.insert(playlistEntity);
        return Result.ok();
    }

    @Override
    public Object getPlayListByUserId() {
        Long userId = currentSubject.getUserId();
        // 组合查询
        List<NewEnvelopeEntity> po = inboxMapper.selectPlayListByUserId(userId);
        List<NewEnvelopeDto> dto = EntityConvertUtil.toNewEnvelopeDto(po);
        return Result.ok(dto);
    }

    @Override
    public Result<Object> delEpisode(NewEnvelopeVo envelopeVo) {
        Long userId = currentSubject.getUserId();
        // 收件箱
        inboxService.update(new LambdaUpdateWrapper<AudioInboxEntity>()
                .set(AudioInboxEntity::getInQueue, 0)
                .eq(AudioInboxEntity::getId, envelopeVo.getId())
                .eq(AudioInboxEntity::getUserId, userId)
        );
        this.remove(new LambdaQueryWrapper<AudioPlaylistEntity>()
                .eq(AudioPlaylistEntity::getPartId, envelopeVo.getId().toString())
                .eq(AudioPlaylistEntity::getUserId, userId)
        );
        return Result.ok();
    }

}
