package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.po.RoleEntity;
import com.zoyo.web.mapper.RoleMapper;
import com.zoyo.web.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色管理表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

}
