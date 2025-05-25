package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.GameScore;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameScoreRepository extends MongoRepository<GameScore, String> {
    Optional<GameScore> findById(String id); // id가 appid인 경우
    Optional<GameScore> findByAppid(Integer appid);
}
