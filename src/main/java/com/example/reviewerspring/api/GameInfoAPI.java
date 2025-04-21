package com.example.reviewerspring.api;

import com.example.reviewerspring.Repository.GameInfoRepository;
import com.example.reviewerspring.domain.GameMongo;
import com.example.reviewerspring.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameInfoAPI {
    //service에서 받아온 값을 호출온곳에 전송 -> controller(react로 개발)
    private final GameService gameService;

    private final GameInfoRepository gameInfoRepository;

    public GameInfoAPI(GameService gameService, GameInfoRepository gameInfoRepository) {
        this.gameService = gameService;
        this.gameInfoRepository = gameInfoRepository;
    }

    @GetMapping("/search/{GAME_PK}")
    public List<GameMongo> searchGameByName(@RequestParam("name") String name) {
        return gameInfoRepository.findByNameContainingIgnoreCase(name);
    }
}
