package com.zoyo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoyo.common.po.AudioInboxEntity;
import com.zoyo.common.po.NewEnvelopeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 播客收件箱表 Mapper 接口
 * </p>
 *
 * @author mxx
 */
@Mapper
public interface AudioInboxMapper extends BaseMapper<AudioInboxEntity> {
    /**
     * 将新数据写入收件箱数据库
     *
     * @param newItemId itemId
     * @param userId    userId
     * @return 更新数量
     */
    int insertBatchByItemId(@Param("userId") String userId, @Param("list") List<String> newItemId);

    /**
     * 将新数据写入收件箱数据库
     *
     * @param list list
     * @return 更新数量
     */
    int insertBatchByEntity(@Param("list") List<AudioInboxEntity> list);

    /**
     * 根据userId查询收件箱
     *
     * @param userId userId
     * @return list
     */
    List<NewEnvelopeEntity> selectNewEnvelopeByUserId(@Param("userId") Long userId);

    /**
     * 获取播放列表
     *
     * @param userId userId
     * @return list
     */
    List<NewEnvelopeEntity> selectPlayListByUserId(@Param("userId") Long userId);

    /**
     * 获取收藏列表
     *
     * @param userId userId
     * @return list
     */
    List<NewEnvelopeEntity> selectCollectListByUserId(@Param("userId") Long userId);

}
