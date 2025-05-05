package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.UserWishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWishlistRepository extends MongoRepository<UserWishlist, String> {
    List<UserWishlist> findByUserId(String userId);
    void deleteByUserIdAndGameId(String userId, String gameId);
    void deleteByUserId(String userId);
}
