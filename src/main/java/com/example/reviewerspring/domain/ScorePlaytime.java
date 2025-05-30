package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "score_playtime")
@Getter @Setter
public class ScorePlaytime {
    @Id
    private String id;

    @Field("app_id")
    private Integer appid;
    private String year_month;
    private double final_score;
    private Integer playtime_forever;
}
