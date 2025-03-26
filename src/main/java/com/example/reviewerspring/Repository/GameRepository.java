package com.example.reviewerspring.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GameRepository{

    private final EntityManager em;
    //db 조회 쿼리 -> service로 조회값 보내기

}