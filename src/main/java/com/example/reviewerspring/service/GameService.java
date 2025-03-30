package com.example.reviewerspring.service;

import com.example.reviewerspring.Repository.GameRepository;
import com.example.reviewerspring.domain.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    //repository에서 데이터 받아와서 api에 전달
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> getGameInfo(String gameId) {
        return null;
    }

    public List<Game> getTopRankedGames() {
        return null;
    }

    public List<Game> getTopRankedGamesByUserTag(String tag) {
        return null;
    }

    public List<Game> searchGames(String title) {
        return null;
    }

    public List<Game> getWishListGames(String userId) {
        return null;
    }

    public List<Game> compareWishListGames(String userId) {
        return null;
    }

}
