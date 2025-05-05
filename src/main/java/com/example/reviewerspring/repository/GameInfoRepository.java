package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GameInfoRepository extends MongoRepository<Game, String> {
    Game findByAppid(String appid);
    List<Game> findByNameContainingIgnoreCase(String name); // 부분 검색, 대소문자 무시
}

