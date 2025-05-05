package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.UserTagRelate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagRelateRepository extends MongoRepository<UserTagRelate, String> {
    List<UserTagRelate> findByUserId(String userId);
    Optional<UserTagRelate> findByUserIdAndTagId(String userId, Integer tagId);
    void deleteByUserId(String userId);
}
