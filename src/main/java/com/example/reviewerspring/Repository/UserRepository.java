package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserId(String userId);
    void deleteByUserId(String userId);
}
