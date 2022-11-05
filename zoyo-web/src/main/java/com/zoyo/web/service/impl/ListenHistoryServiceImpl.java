package com.zoyo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoyo.web.mapper.ListenHistoryMapper;
import com.zoyo.common.po.ListenHistoryEntity;
import com.zoyo.web.service.IListenHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收听历史表 服务实现类
 * </p>
 *
 * @author mxx
 */
@Service
public class ListenHistoryServiceImpl extends ServiceImpl<ListenHistoryMapper, ListenHistoryEntity> implements IListenHistoryService {

}
