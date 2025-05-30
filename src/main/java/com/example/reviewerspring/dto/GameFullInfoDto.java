package com.example.reviewerspring.dto;

import com.example.reviewerspring.domain.GameTag;
import com.example.reviewerspring.domain.ScoreByDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GameFullInfoDto {

    private Integer appid;
    private String gameName;
    private String name;
    private String description;
    private List<String> genres;
    private List<String> categories;
    private String image;
    private String releaseDate;
    private Integer price;
    private String priceText;
    private Integer discount;

    private Double score;
    private List<ScoreByDate> scoreByDate;
    private String posiWord;
    private String negaWord;

    private Integer avgPlaytime;
    private Integer top10per;

    private LocalDateTime updateDate;

    private List<GameTag> tags;
}
