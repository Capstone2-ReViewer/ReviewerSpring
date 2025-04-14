package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserWishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWishlistRepository extends MongoRepository<UserWishlist, Long> {
    List<UserWishlist> findByUserId(String userId);

    boolean existsByUserIdAndGameId(String userId, String gameId);

    void deleteByUserIdAndGameId(String userId, String gameId);
}
