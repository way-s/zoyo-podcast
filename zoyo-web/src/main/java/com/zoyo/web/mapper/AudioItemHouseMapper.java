package com.zoyo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoyo.common.po.AudioItemHouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 每期节目信息表 Mapper 接口
 * </p>
 *
 * @author mxx
 */
@Mapper
public interface AudioItemHouseMapper extends BaseMapper<AudioItemHouseEntity> {
    /**
     * 批量插入
     * @param list list
     * @return boolean
     */
    int insertBatch(@Param("list") List<AudioItemHouseEntity> list);
}

