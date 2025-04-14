package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByTitleContaining(String title);
    List<Game> findAllByOrderByScoreDesc();
}
