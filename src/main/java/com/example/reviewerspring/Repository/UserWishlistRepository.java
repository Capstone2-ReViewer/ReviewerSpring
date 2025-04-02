package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWishlistRepository extends JpaRepository<UserWishlist, Long> {
    List<UserWishlist> findByUserId(String userId);

    boolean existsByUserIdAndGameId(String userId, String gameId);

    void deleteByUserIdAndGameId(String userId, String gameId);
}
