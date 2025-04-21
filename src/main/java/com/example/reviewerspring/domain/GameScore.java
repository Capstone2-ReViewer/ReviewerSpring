package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "game_score")
@Getter @Setter
public class GameScore {
    @Id
    @Column(name = "GAME_PK")
    private String gamePk;

    @OneToOne
    @MapsId
    @JoinColumn(name = "GAME_PK")
    private Game game;

    private double score;
    private double scorebydate;

    @Column(name = "posi_word")
    private String posiWord;

    @Column(name = "nega_word")
    private String negaWord;
}