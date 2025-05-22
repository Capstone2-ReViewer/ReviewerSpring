package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.Game;
import com.example.reviewerspring.domain.GameScore;
import com.example.reviewerspring.domain.Playtime;
import com.example.reviewerspring.domain.UserWishlist;
import com.example.reviewerspring.dto.GameDetailResponse;
import com.example.reviewerspring.repository.GameRepository;
import com.example.reviewerspring.repository.UserTagPreferredRepository;
import com.example.reviewerspring.repository.UserWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserWishlistRepository wishlistRepository;
    private final UserTagPreferredRepository userTagPreferredRepository;
    //repository에서 데이터 받아와서 api에 전달
    public GameService(GameRepository gameRepository, UserWishlistRepository wishlistRepository, UserTagPreferredRepository userTagPreferredRepository) {
        this.gameRepository = gameRepository;
        this.wishlistRepository = wishlistRepository;
        this.userTagPreferredRepository = userTagPreferredRepository;
    }

    public GameDetailResponse getGameDetail(Integer appid) {
        Game game = gameRepository.findByAppid(appid)
                .orElseThrow(() -> new RuntimeException("게임을 찾을 수 없습니다."));

        // 평균 점수 등
        GameScore score = game.getScore();
        Playtime playtime = game.getPlaytime();

        // 중위값과 표준편차는 간단 예시로 고정 값 또는 null 처리 가능
        double median = score.getScore(); // 샘플 처리
        double stdDev = 1.2; // 추후 계산

        // 비슷한 게임: 같은 장르 중에서 자신 제외하고 일부만
        List<Game> similar = gameRepository.findByGenresIn(game.getGenres()).stream()
                .filter(g -> !g.getAppid().equals(appid))
                .limit(5)
                .toList();

        List<GameDetailResponse.SimilarGameDto> similarDtos = similar.stream().map(g ->
                new GameDetailResponse.SimilarGameDto(g.getGame_name(), g.getImage(), String.valueOf(g.getAppid()))
        ).toList();

        return new GameDetailResponse(
                game.getGame_name(),
                game.getImage(),
                game.getDescription(),
                game.getGenres(),
                score.getPosiWord(),
                score.getNegaWord(),
                new GameDetailResponse.ScoreTrend(
                        score.getScore(),
                        playtime.getTop10per(),
                        median,
                        stdDev
                ),
                similarDtos
        );
    }

    // 게임 찜 토글
    public boolean toggleWishlist(String userId, String gameId) {
        Optional<UserWishlist> wishlistEntry = wishlistRepository.findByUserIdAndGameId(userId, gameId);
        if (wishlistEntry.isPresent()) {
            wishlistRepository.delete(wishlistEntry.get());
            return false; // 제거됨
        } else {
            UserWishlist newWishlist = new UserWishlist();
            newWishlist.setUserId(userId);
            newWishlist.setGameId(gameId);
            wishlistRepository.save(newWishlist);
            return true; // 추가됨
        }
    }

    // 찜 목록
    public List<Game> getWishListGames(String userId) {
        List<UserWishlist> wishlists = wishlistRepository.findByUserId(userId);
        List<String> gameIds = wishlists.stream().map(UserWishlist::getGameId).toList();
        return gameRepository.findByIdIn(gameIds);
    }

    // 전체 게임 순위 출력 (점수 기준 내림차순)
    public List<Game> getTopRankedGames() {
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .sorted(Comparator.comparingDouble(g -> -g.getScore().getScore()))
                .collect(Collectors.toList());
    }

    // 유저 선호 태그 기반 게임 순위
    public List<Game> getTopRankedGamesByUserTag(String userId) {
        List<Integer> preferredTagIds = userTagPreferredRepository.findByUserPk(userId).stream()
                .map(p -> p.getTagId())
                .toList();

        List<Game> allGames = gameRepository.findAll();

        return allGames.stream()
                .filter(game -> game.getTags() != null && game.getTags().stream()
                        .anyMatch(tag -> preferredTagIds.contains(tag.getTagPk())))
                .sorted(Comparator.comparingDouble(g -> -g.getScore().getScore()))
                .collect(Collectors.toList());
    }

    // 찜 게임 비교 (단순 리스트 반환)
    public List<Game> compareWishListGames(String userId) {
        return getWishListGames(userId);
    }

}
