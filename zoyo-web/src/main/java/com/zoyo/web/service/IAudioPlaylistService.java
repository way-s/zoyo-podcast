package com.zoyo.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.po.AudioPlaylistEntity;
import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;

/**
 * <p>
 * 播放列表表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IAudioPlaylistService extends IService<AudioPlaylistEntity> {

    /**
     * 添加节目到播放列表
     *
     * @param envelopeVo envelopeVo
     * @return result
     */
    Result<Object> addEpisodeToPlayList(NewEnvelopeVo envelopeVo);

    /**
     * 获取播放列表
     *
     * @return result
     */
    Object getPlayListByUserId();

    /**
     * 删除
     * @param envelopeVo envelopeVo
     * @return result
     */
    Result<Object> delEpisode(NewEnvelopeVo envelopeVo);

}
