package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserTagPreferred;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagPreferredRepository extends MongoRepository<UserTagPreferred, Integer> {
    List<UserTagPreferred> findByUserPk(Integer userPk);
}
