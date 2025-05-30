package com.example.reviewerspring.domain;

import com.example.reviewerspring.dto.SimpleKeyword;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@Document(collection = "game_score")
public class GameScore {
    @Id
    private String id;

    private Integer appid;
    private double score;
    private List<ScoreByDate> scorebydate;

    private List<SimpleKeyword> posiWord;
    private List<SimpleKeyword> negaWord;
}

