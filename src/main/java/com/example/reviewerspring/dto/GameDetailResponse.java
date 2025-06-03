package com.example.reviewerspring.dto;

import com.example.reviewerspring.domain.ScoreByDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDetailResponse {
    private String title;
    private String image;
    private String description;
    private List<String> genres;
    private List<SimpleKeyword> posiWord;
    private List<SimpleKeyword> negaWord;

    private ScoreTrend scoreTrend;

    private List<SimilarGameDto> similarGames;
    private List<ScoreByDate> scoreByDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScoreTrend {
        private Double average;
        private Integer top10Percent;
        private Integer avg;
        private Double stdDev;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimilarGameDto {
        private String title;
        private String image;
        private String appid;
    }
}
