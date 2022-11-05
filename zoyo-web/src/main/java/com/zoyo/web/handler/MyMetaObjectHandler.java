package com.zoyo.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zoyo.web.util.CurrentSubject;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: mxx
 * @Description:
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private CurrentSubject currentSubject;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);

        this.setFieldValByName("createBy",
                currentSubject.getUserAccount() != null ? currentSubject.getUserAccount() : "system"
                , metaObject);
        this.setFieldValByName("updateBy",
                currentSubject.getUserAccount() != null ? currentSubject.getUserAccount() : "system"
                , metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy",
                currentSubject.getUserAccount() != null ? currentSubject.getUserAccount() : "system"
                , metaObject);
    }
}
