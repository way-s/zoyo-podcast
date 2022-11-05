package com.zoyo.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.po.AudioItemHouseEntity;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 每期节目信息表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IAudioItemHouseService extends IService<AudioItemHouseEntity> {

    /**
     * 更新播客最近的节目
     *
     * @param entities list
     * @return 更新数量
     */
    Future<Integer> updatePodCastItems(List<AudioItemHouseEntity> entities);

    int updatePodCastItems2(List<AudioItemHouseEntity> entities);

}
