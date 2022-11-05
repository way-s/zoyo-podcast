package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.dto.NewEnvelopeDto;
import com.zoyo.common.exception.Asserts;
import com.zoyo.common.po.AudioCollectsEntity;
import com.zoyo.common.po.AudioInboxEntity;
import com.zoyo.common.po.NewEnvelopeEntity;
import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;
import com.zoyo.web.mapper.AudioCollectsMapper;
import com.zoyo.web.mapper.AudioInboxMapper;
import com.zoyo.web.service.IAudioCollectsService;
import com.zoyo.web.service.IAudioInboxService;
import com.zoyo.web.util.CurrentSubject;
import com.zoyo.web.util.EntityConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 播客收藏表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Service
public class AudioCollectsServiceImpl extends ServiceImpl<AudioCollectsMapper, AudioCollectsEntity> implements IAudioCollectsService {

    @Resource
    private CurrentSubject currentSubject;

    @Resource
    private AudioInboxMapper inboxMapper;

    @Resource
    private IAudioInboxService inboxService;

    @Override
    public Result<Object> addEpisodeToCollectList(NewEnvelopeVo envelopeVo) {
        Long userId = currentSubject.getUserId();
        AudioCollectsEntity one = this.getOne(new LambdaQueryWrapper<AudioCollectsEntity>()
                .select(AudioCollectsEntity::getId)
                .eq(AudioCollectsEntity::getPartId, envelopeVo.getId().toString())
                .eq(AudioCollectsEntity::getUserId, userId.toString())
        );
        if (one != null) {
            Asserts.fail("已收藏");
        }
        // 收件箱
        inboxService.update(new LambdaUpdateWrapper<AudioInboxEntity>()
                .set(AudioInboxEntity::getCollect, 1)
                .eq(AudioInboxEntity::getId, envelopeVo.getId())
                .eq(AudioInboxEntity::getUserId, userId)
        );
        // 播放列表
        AudioCollectsEntity collectsEntity = new AudioCollectsEntity()
                .setUserId(userId)
                .setPartId(envelopeVo.getId().toString());
        this.save(collectsEntity);
        return Result.ok();
    }

    @Override
    public Object getCollectListByUserId() {
        Long userId = currentSubject.getUserId();
        // 组合查询
        List<NewEnvelopeEntity> po = inboxMapper.selectCollectListByUserId(userId);
        List<NewEnvelopeDto> dto = EntityConvertUtil.toNewEnvelopeDto(po);
        return Result.ok(dto);
    }

    @Override
    public Result<Object> delEpisode(NewEnvelopeVo envelopeVo) {
        Long userId = currentSubject.getUserId();
        // 收件箱
        inboxService.update(new LambdaUpdateWrapper<AudioInboxEntity>()
                .set(AudioInboxEntity::getCollect, 0)
                .eq(AudioInboxEntity::getId, envelopeVo.getId())
                .eq(AudioInboxEntity::getUserId, userId)
        );
        this.remove(new LambdaQueryWrapper<AudioCollectsEntity>()
                .eq(AudioCollectsEntity::getPartId, envelopeVo.getId().toString())
                .eq(AudioCollectsEntity::getUserId, userId)
        );
        return Result.ok();
    }

}
