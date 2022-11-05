package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.common.po.AdminEntity;
import com.zoyo.web.mapper.AdminMapper;
import com.zoyo.web.service.IAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminEntity> implements IAdminService {

}
