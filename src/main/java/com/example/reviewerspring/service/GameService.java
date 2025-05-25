package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.*;
import com.example.reviewerspring.dto.GameDetailResponse;
import com.example.reviewerspring.dto.GameFullInfoDto;
import com.example.reviewerspring.repository.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameScoreRepository gameScoreRepository;
    private final PlaytimeRepository playtimeRepository;
    private final UpdateRepository updateRepository;
    private final GameTagRepository gameTagRepository;
    private final UserWishlistRepository wishlistRepository;
    private final UserTagPreferredRepository userTagPreferredRepository;
    //repository에서 데이터 받아와서 api에 전달
    public GameService(GameRepository gameRepository, GameScoreRepository gameScoreRepository, PlaytimeRepository playtimeRepository, UpdateRepository updateRepository, GameTagRepository gameTagRepository, UserWishlistRepository wishlistRepository, UserTagPreferredRepository userTagPreferredRepository) {
        this.gameRepository = gameRepository;
        this.gameScoreRepository = gameScoreRepository;
        this.playtimeRepository = playtimeRepository;
        this.updateRepository = updateRepository;
        this.gameTagRepository = gameTagRepository;
        this.wishlistRepository = wishlistRepository;
        this.userTagPreferredRepository = userTagPreferredRepository;
    }

    public GameFullInfoDto getFullGameInfo(Integer appid) {
        Game game = gameRepository.findByAppid(appid)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        GameScore score = gameScoreRepository.findByAppid(appid).orElse(null);
        Playtime playtime = playtimeRepository.findByAppid(appid).orElse(null);
        Update update = updateRepository.findByAppid(appid).orElse(null);
        List<GameTag> tags = gameTagRepository.findByGamePk(appid);

        GameFullInfoDto dto = new GameFullInfoDto();
        dto.setAppid(game.getAppid());
        dto.setGameName(game.getGame_name());
        dto.setName(game.getName());
        dto.setDescription(game.getDescription());
        dto.setGenres(game.getGenres());
        dto.setCategories(game.getCategories());
        dto.setImage(game.getImage());
        dto.setReleaseDate(game.getRelease_date());
        dto.setPrice(game.getPrice());
        dto.setPriceText(game.getPrice_text());
        dto.setDiscount(game.getDiscount());

        if (score != null) {
            dto.setScore(score.getScore());
            dto.setScoreByDate(score.getScorebydate());
            dto.setPosiWord(score.getPosiWord());
            dto.setNegaWord(score.getNegaWord());
        }

        if (playtime != null) {
            dto.setAvgPlaytime(playtime.getAvg());
            dto.setTop10per(playtime.getTop10per());
        }

        if (update != null) {
            dto.setUpdateDate(update.getUpdateDate());
        }

        dto.setTags(tags);

        return dto;
    }

    public GameDetailResponse getGameDetail(Integer appid) {
        Game game = gameRepository.findByAppid(appid)
                .orElseThrow(() -> new RuntimeException("게임을 찾을 수 없습니다."));

        GameScore score = gameScoreRepository.findByAppid(appid).orElse(null);
        Playtime playtime = playtimeRepository.findByAppid(appid).orElse(null);

        double median = score != null ? score.getScore() : 0.0;
        double stdDev = 1.2; // 필요시 계산

        // 비슷한 게임
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
                score != null ? score.getPosiWord() : null,
                score != null ? score.getNegaWord() : null,
                new GameDetailResponse.ScoreTrend(
                        score != null ? score.getScore() : null,
                        playtime != null ? playtime.getTop10per() : null,
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
                .map(game -> {
                    GameScore score = gameScoreRepository.findByAppid(game.getAppid()).orElse(null);
                    double s = score != null ? score.getScore() : 0.0;
                    return new Object[] { game, s };
                })
                .sorted((a, b) -> Double.compare((double)b[1], (double)a[1]))
                .map(pair -> (Game) pair[0])
                .collect(Collectors.toList());
    }

    // 유저 선호 태그 기반 게임 순위
    public List<Game> getTopRankedGamesByUserTag(String userId) {
        List<Integer> preferredTagIds = userTagPreferredRepository.findByUserId(userId).stream()
                .map(UserTagPreferred::getTagId)
                .toList();

        List<Game> allGames = gameRepository.findAll();

        return allGames.stream()
                .filter(game -> {
                    List<GameTag> tags = gameTagRepository.findByGamePk(game.getAppid());
                    return tags.stream().anyMatch(tag -> preferredTagIds.contains(tag.getTagPk()));
                })
                .map(game -> {
                    GameScore score = gameScoreRepository.findByAppid(game.getAppid()).orElse(null);
                    double s = score != null ? score.getScore() : 0.0;
                    return new Object[] { game, s };
                })
                .sorted((a, b) -> Double.compare((double)b[1], (double)a[1]))
                .map(pair -> (Game) pair[0])
                .collect(Collectors.toList());
    }

    // 찜 게임 비교 (단순 리스트 반환)
    public List<Game> compareWishListGames(String userId) {
        return getWishListGames(userId);
    }

}
