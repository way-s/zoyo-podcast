package com.zoyo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoyo.common.po.AudioRssEntity;
import com.zoyo.common.vo.PodcastSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 播客rss表 Mapper 接口
 * </p>
 *
 * @author mxx
 */
@Mapper
public interface AudioRssMapper extends BaseMapper<AudioRssEntity> {
    /**
     * 根据feedId获取播客摘要
     *
     * @param userId userId
     * @return list
     */
    List<PodcastSummary> getPodcastSummaryByFeedId(@Param("userId") Long userId);

}
