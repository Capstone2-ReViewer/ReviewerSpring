package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.GameTag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameTagRepository extends MongoRepository<GameTag, String> {
    List<GameTag> findByGamePk(Integer gamePk);
}
