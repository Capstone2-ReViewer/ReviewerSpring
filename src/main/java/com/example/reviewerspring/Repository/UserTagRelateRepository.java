package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserTagRelate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagRelateRepository extends MongoRepository<UserTagRelate, Integer> {
    List<UserTagRelate> findByUserPk(Integer userPk);
}