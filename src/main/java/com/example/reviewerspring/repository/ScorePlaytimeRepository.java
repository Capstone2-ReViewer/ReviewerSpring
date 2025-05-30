package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.ScorePlaytime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScorePlaytimeRepository extends MongoRepository<ScorePlaytime, String> {
    List<ScorePlaytime> findByAppid(Integer appid);
}
