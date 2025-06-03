package com.example.reviewerspring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WishlistGameResponse {
    private Integer appid;
    private String title;
    private String image;

    private Double score;
    private Integer count;
    private List<SimpleKeyword> posiWord;
    private List<SimpleKeyword> negaWord;

    private Integer avgPlaytime;
    private Integer top10per;
}