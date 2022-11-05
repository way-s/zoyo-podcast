package com.zoyo.common.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: mxx
 * @Description:
 */
@Data
@Accessors(chain = true)
@TableName("zy_audio_subscribe")
public class UserSubscribe implements Serializable {

    private static final long serialVersionUID = -1500981383483501384L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订阅的播客 feedId
     */
    private List<String> lists;

}



