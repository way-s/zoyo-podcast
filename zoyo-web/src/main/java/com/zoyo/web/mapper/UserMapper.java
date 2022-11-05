package com.zoyo.web.mapper;

import com.zoyo.common.po.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author mxx
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
