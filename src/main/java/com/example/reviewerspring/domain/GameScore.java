package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "game_score")
@Getter @Setter
public class GameScore {
    @Id
    private String id;

    private double score;
    private String scorebydate;

    private String posiWord;
    private String negaWord;
}
