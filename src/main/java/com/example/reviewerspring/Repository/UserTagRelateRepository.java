package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserTagRelate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagRelateRepository extends JpaRepository<UserTagRelate, Integer> {
    List<UserTagRelate> findByUserPk(Integer userPk);
}