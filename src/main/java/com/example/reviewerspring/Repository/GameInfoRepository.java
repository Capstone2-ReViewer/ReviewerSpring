package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.GameMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GameInfoRepository extends MongoRepository<GameMongo, String> {
    GameMongo findByAppid(String appid);
    List<GameMongo> findByNameContainingIgnoreCase(String name); // 부분 검색, 대소문자 무시
}

