package com.example.reviewerspring.api;

import com.example.reviewerspring.Repository.GameInfoRepository;
import com.example.reviewerspring.domain.GameMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class TestAPI {
    @Autowired
    private GameInfoRepository gameInfoRepository;

    @GetMapping("/search")
    public List<GameMongo> searchGameByName(@RequestParam("name") String name) {
        return gameInfoRepository.findByNameContainingIgnoreCase(name);
    }
}
