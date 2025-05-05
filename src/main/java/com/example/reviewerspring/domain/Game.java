package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game_info")
@Getter
@Setter
public class Game {
    @Id
    private String id;
    private Integer appid;

    private String game_name;
    private String name;

    private String description;

    private List<String> genres;
    private List<String> categories;

    private String image;
    private String release_date;

    private Integer price;
    private String price_text;

    private Integer discount;

    private GameScore score;
    private Playtime playtime;
    private Update update;
    private List<GameTag> tags;
}
