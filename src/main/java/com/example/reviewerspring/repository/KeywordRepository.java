package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Keywords;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KeywordRepository extends MongoRepository<Keywords, String> {
    @Query("{ 'app_id' : ?0 }")
    List<Keywords> findByAppId(Integer appId);
}