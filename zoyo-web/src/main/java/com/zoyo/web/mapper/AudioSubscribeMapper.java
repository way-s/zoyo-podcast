package com.zoyo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoyo.common.po.AudioSubscribeEntity;
import com.zoyo.common.po.UserSubscribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 播客订阅表 Mapper 接口
 * </p>
 *
 * @author mxx
 */
@Mapper
public interface AudioSubscribeMapper extends BaseMapper<AudioSubscribeEntity> {

    /**
     * 获取每个用户的订阅数据
     *
     * @param list ids
     * @return list
     */
    List<UserSubscribe> getUsersSubscribeInfo(@Param("list") List<AudioSubscribeEntity> list);

    /**
     * 获取单个用户的订阅
     *
     * @param userId userId
     * @return feedIds
     */
    List<UserSubscribe> getSingleUserSubscribeInfo(@Param("userId") Long userId);

}
