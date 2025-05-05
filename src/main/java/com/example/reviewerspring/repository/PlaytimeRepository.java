package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Playtime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaytimeRepository extends MongoRepository<Playtime, String> {
    Optional<Playtime> findById(String gameId);
}

