package com.zoyo.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.po.AudioCollectsEntity;
import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;

/**
 * <p>
 * 播客收藏表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IAudioCollectsService extends IService<AudioCollectsEntity> {

    /**
     * 收藏节目
     *
     * @param envelopeVo envelopeVo
     * @return result
     */
    Result<Object> addEpisodeToCollectList(NewEnvelopeVo envelopeVo);

    /**
     * 获取收藏列表
     *
     * @return result
     */
    Object getCollectListByUserId();

    /**
     * 删除
     * @param envelopeVo envelopeVo
     * @return result
     */
    Result<Object> delEpisode(NewEnvelopeVo envelopeVo);

}
