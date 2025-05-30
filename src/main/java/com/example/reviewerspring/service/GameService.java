package com.example.reviewerspring.service;

import com.example.reviewerspring.domain.*;
import com.example.reviewerspring.dto.GameDetailResponse;
import com.example.reviewerspring.dto.GameFullInfoDto;
import com.example.reviewerspring.dto.SimpleKeyword;
import com.example.reviewerspring.repository.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//import javax.management.Query;
import java.util.*;
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
    private final ScorePlaytimeRepository scorePlaytimeRepository;
    private final MongoTemplate mongoTemplate;

    //repository에서 데이터 받아와서 api에 전달
    public GameService(GameRepository gameRepository, GameScoreRepository gameScoreRepository, PlaytimeRepository playtimeRepository, UpdateRepository updateRepository, GameTagRepository gameTagRepository, UserWishlistRepository wishlistRepository, UserTagPreferredRepository userTagPreferredRepository, ScorePlaytimeRepository scorePlaytimeRepository, MongoTemplate mongoTemplate) {
        this.gameRepository = gameRepository;
        this.gameScoreRepository = gameScoreRepository;
        this.playtimeRepository = playtimeRepository;
        this.updateRepository = updateRepository;
        this.gameTagRepository = gameTagRepository;
        this.wishlistRepository = wishlistRepository;
        this.userTagPreferredRepository = userTagPreferredRepository;
        this.scorePlaytimeRepository = scorePlaytimeRepository;
        this.mongoTemplate = mongoTemplate;
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

    public List<Map<String, Object>> getSimpleGameList() {
        List<Game> allGames = gameRepository.findAll();

        return allGames.stream()
                .map(game -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("appid", game.getAppid());
                    map.put("game_name", game.getGame_name());
                    return map;
                })
                .toList();
    }

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행 (1000ms * 60 * 60)
    public void updateGameScoresFromPlaytimeHourly() {
        System.out.println("[스케줄링] 게임 점수 업데이트 시작");
        updateGameScoresFromPlaytime();
        System.out.println("[스케줄링] 게임 점수 업데이트 완료");
    }

    public void updateGameScoresFromPlaytime() {
        Map<Integer, List<ScorePlaytime>> grouped = scorePlaytimeRepository.findAll()
                .stream()
                .filter(s -> s.getAppid() != null)
                .collect(Collectors.groupingBy(ScorePlaytime::getAppid));

        for (Map.Entry<Integer, List<ScorePlaytime>> entry : grouped.entrySet()) {
            Integer appid = entry.getKey();
            List<ScorePlaytime> scoreList = entry.getValue();

            double averageScore = scoreList.stream()
                    .mapToDouble(ScorePlaytime::getFinal_score)
                    .average()
                    .orElse(0.0);
            averageScore = Math.round(averageScore * 100) / 100.0;

            // year_month 별 평균 점수 Map (정렬된 TreeMap)
            Map<String, Double> avgByMonth = scoreList.stream()
                    .collect(Collectors.groupingBy(
                            ScorePlaytime::getYear_month,
                            TreeMap::new,
                            Collectors.averagingDouble(ScorePlaytime::getFinal_score)
                    ));

            // ScoreByDate 리스트 생성
            List<ScoreByDate> scoreByDateList = avgByMonth.entrySet()
                    .stream()
                    .map(e -> new ScoreByDate(e.getKey(), Math.round(e.getValue() * 100) / 100.0))
                    .toList();

            // 플레이타임 평균 계산
            double averagePlaytime = scoreList.stream()
                    .mapToInt(ScorePlaytime::getPlaytime_forever)
                    .average()
                    .orElse(0.0);
            int avgPlaytimeInt = (int) Math.round(averagePlaytime);

            // 상위 10퍼센트 플레이타임 계산
            List<Integer> playtimes = scoreList.stream()
                    .map(ScorePlaytime::getPlaytime_forever)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            int top10Count = (int) Math.ceil(playtimes.size() * 0.1);
            top10Count = Math.max(top10Count, 1);
            List<Integer> top10Playtimes = playtimes.subList(0, top10Count);

            double top10Avg = top10Playtimes.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
            int top10perInt = (int) Math.round(top10Avg);

            // game_score 업데이트
            GameScore gameScore = gameScoreRepository.findByAppid(appid).orElse(new GameScore());
            gameScore.setAppid(appid);
            gameScore.setScore(averageScore);
            gameScore.setScorebydate(scoreByDateList);
            gameScoreRepository.save(gameScore);

            // playtime 업데이트
            Playtime playtime = playtimeRepository.findByAppid(appid).orElse(new Playtime());
            playtime.setAppid(appid);
            playtime.setAvg(avgPlaytimeInt);
            playtime.setTop10per(top10perInt);
            playtimeRepository.save(playtime);

            System.out.println("[점수 저장] appid=" + appid + ", score=" + averageScore + ", scorebydate=" + scoreByDateList);
            System.out.println("[플레이타임 저장] appid=" + appid + ", avg=" + avgPlaytimeInt + ", top10per=" + top10perInt);
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void updateGameKeywordsScheduled() {
        updateGameKeywords();
    }

    // GameService.java 에서 updateGameKeywords 메서드
    public void updateGameKeywords() {
        List<Keywords> allKeywords = mongoTemplate.findAll(Keywords.class, "keywords");

        Map<Integer, List<Keywords>> posiMap = new HashMap<>();
        Map<Integer, List<Keywords>> negaMap = new HashMap<>();

        for (Keywords k : allKeywords) {
            int appId = k.getApp_id();
            if ("positive".equalsIgnoreCase(k.getSentiment())) {
                posiMap.computeIfAbsent(appId, id -> new ArrayList<>()).add(k);
            } else if ("negative".equalsIgnoreCase(k.getSentiment())) {
                negaMap.computeIfAbsent(appId, id -> new ArrayList<>()).add(k);
            }
        }

        Set<Integer> allAppIds = new HashSet<>();
        allAppIds.addAll(posiMap.keySet());
        allAppIds.addAll(negaMap.keySet());

        for (Integer appId : allAppIds) {
            if (appId == null) continue;

            Query query = new Query();
            query.addCriteria(Criteria.where("appid").is(appId));

            GameScore gameScore = mongoTemplate.findOne(query, GameScore.class);

            if (gameScore != null) {
                List<SimpleKeyword> posiList = posiMap.getOrDefault(appId, new ArrayList<>())
                        .stream()
                        .map(k -> new SimpleKeyword(k.getKeyword(), k.getCount()))
                        .collect(Collectors.toList());

                List<SimpleKeyword> negaList = negaMap.getOrDefault(appId, new ArrayList<>())
                        .stream()
                        .map(k -> new SimpleKeyword(k.getKeyword(), k.getCount()))
                        .collect(Collectors.toList());

                gameScore.setPosiWord(posiList);
                gameScore.setNegaWord(negaList);
                mongoTemplate.save(gameScore);
            }
        }


        System.out.println("⏰ 1시간마다 자동으로 키워드 업데이트 완료!");
    }


}
