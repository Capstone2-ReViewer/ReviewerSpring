package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.UserTagPreferred;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagPreferredRepository extends MongoRepository<UserTagPreferred, String> {
    List<UserTagPreferred> findByUserId(String userId);
    void deleteByUserId(String userId);
}

