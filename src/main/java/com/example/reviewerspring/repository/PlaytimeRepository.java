package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Playtime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaytimeRepository extends MongoRepository<Playtime, String> {
    Optional<Playtime> findById(String id);
    Optional<Playtime> findByAppid(Integer appid);
    List<Playtime> findByAppidIn(List<Integer> appIds);
}

