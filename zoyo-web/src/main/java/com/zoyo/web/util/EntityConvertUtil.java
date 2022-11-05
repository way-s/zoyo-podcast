package com.zoyo.web.util;

import com.zoyo.common.dto.NewEnvelopeDto;
import com.zoyo.common.po.NewEnvelopeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mxx
 * @Description: NewEnvelope实体类转换
 */
public class EntityConvertUtil {

    /**
     * po转dto
     *
     * @param entities po list
     * @return dto list
     */
    public static List<NewEnvelopeDto> toNewEnvelopeDto(List<NewEnvelopeEntity> entities) {
        List<NewEnvelopeDto> dto = new ArrayList<>();
        for (NewEnvelopeEntity entity : entities) {
            dto.add(new NewEnvelopeDto().toNewEnvelopeDto(entity));
        }
        return dto;
    }


}
