package com.zoyo.web.mapper;

import com.zoyo.web.bo.AudioContentMg;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: mxx
 * @Description:
 */
@Repository
public interface AudioContentMgMapper extends MongoRepository<AudioContentMg, Long> {
}
