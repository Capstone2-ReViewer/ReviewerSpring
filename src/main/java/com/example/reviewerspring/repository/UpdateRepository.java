package com.example.reviewerspring.repository;

import com.example.reviewerspring.domain.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UpdateRepository extends MongoRepository<Update, String> {
    List<Update> findByUpdateDateAfter(LocalDateTime time);
    Optional<Update> findByAppid(Integer appid);
}
