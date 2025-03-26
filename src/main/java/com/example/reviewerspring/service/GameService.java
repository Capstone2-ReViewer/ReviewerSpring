package com.example.reviewerspring.service;

import com.example.reviewerspring.Repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;
    //repository에서 데이터 받아와서 api에 전달
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }



}
