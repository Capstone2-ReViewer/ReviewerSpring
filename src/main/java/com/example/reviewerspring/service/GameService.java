package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.Game;
import com.example.reviewerspring.domain.GameScore;
import com.example.reviewerspring.domain.Playtime;
import com.example.reviewerspring.dto.GameDetailResponse;
import com.example.reviewerspring.repository.GameRepository;
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

    public List<Game> getTopRankedGames() {
        return null;
    }

    public List<Game> getTopRankedGamesByUserTag(String tag) {
        return null;
    }

    public List<Game> getWishListGames(String userId) {
        return null;
    }

    public List<Game> compareWishListGames(String userId) {
        return null;
    }

}
