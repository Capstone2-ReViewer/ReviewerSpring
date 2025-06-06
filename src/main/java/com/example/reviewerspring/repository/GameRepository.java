package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findByAppid(Integer appid);
    List<Game> findByGenresIn(List<String> genres);
    List<Game> findByIdIn(List<String> ids);
    List<Game> findByAppidIn(List<Integer> appids);
}
