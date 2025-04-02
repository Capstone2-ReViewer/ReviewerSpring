package com.example.reviewerspring.api;

import com.example.reviewerspring.domain.Game;
import com.example.reviewerspring.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameAPI {
    private final GameService gameService;

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameInfo(@PathVariable String gameId) {
        return null;
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<Game>> getGameRankings() {
        return null;
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<Game>> getRecommendedGames(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames() {
        return null;
    }

    @GetMapping("/wishlist/compare/{userId}")
    public ResponseEntity<List<Game>> compareWishlistGames(@PathVariable String userId) {
        return null;
    }
}
