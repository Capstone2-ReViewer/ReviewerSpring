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

    // 게임 찜 추가 or 삭제 (토글)
    @PostMapping("/wishlist")
    public ResponseEntity<String> toggleWishlist(@RequestParam String userId, @RequestParam String gameId) {
        boolean added = gameService.toggleWishlist(userId, gameId);
        return ResponseEntity.ok(added ? "찜 목록에 추가됨" : "찜 목록에서 제거됨");
    }

    // 게임 찜 목록 보기
    @GetMapping("/wishlist")
    public ResponseEntity<List<Game>> getWishlist(@RequestParam String userId) {
        List<Game> wishlist = gameService.getWishListGames(userId);
        return ResponseEntity.ok(wishlist);
    }

    // 전체 게임 순위 (점수 기준)
    @GetMapping("/ranking")
    public ResponseEntity<List<Game>> getTopRankedGames() {
        List<Game> ranked = gameService.getTopRankedGames();
        return ResponseEntity.ok(ranked);
    }

    // 유저 선호 태그 기반 게임 순위
    @GetMapping("/ranking/user-tag")
    public ResponseEntity<List<Game>> getTopRankedGamesByUserTag(@RequestParam String userId) {
        List<Game> personalizedRank = gameService.getTopRankedGamesByUserTag(userId);
        return ResponseEntity.ok(personalizedRank);
    }

    // 찜 게임 간 비교 기능 (게임 정보 간 차이 등 비교용)
    @GetMapping("/wishlist/compare")
    public ResponseEntity<List<Game>> compareWishlist(@RequestParam String userId) {
        List<Game> compared = gameService.compareWishListGames(userId);
        return ResponseEntity.ok(compared);
    }
}
