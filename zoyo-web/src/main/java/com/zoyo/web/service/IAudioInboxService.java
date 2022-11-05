package com.zoyo.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoyo.common.po.AudioInboxEntity;
import com.zoyo.common.vo.Result;

/**
 * <p>
 * 播客收件箱表 服务类
 * </p>
 *
 * @author mxx
 */
public interface IAudioInboxService extends IService<AudioInboxEntity> {
    /**
     * 查看收件箱
     *
     * @return result
     */
    Result<Object> queryNewEnvelope();

    /**
     * 更新用户收件箱
     */
    void updateUserInBox();

}
