package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends MongoRepository<Tag, Integer> {
    List<Tag> findByCategory(String category);
}

