package com.zoyo.web.mapper;

import com.zoyo.web.bo.AudioFeedBasicInfoMg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: mxx
 * @Description:
 */
@Repository
public interface AudioFeedBasicInfoMgMapper extends MongoRepository<AudioFeedBasicInfoMg, Long> {
}
