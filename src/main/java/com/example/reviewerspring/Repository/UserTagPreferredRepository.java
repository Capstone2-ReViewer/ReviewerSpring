package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.UserTagPreferred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagPreferredRepository extends JpaRepository<UserTagPreferred, Integer> {
    List<UserTagPreferred> findByUserPk(Integer userPk);
}
