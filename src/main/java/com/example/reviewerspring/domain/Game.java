package com.example.reviewerspring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Game {
    @Id
    @GeneratedValue()
    @Column(name="GAME_PK")
    private int gamePK;

    private String gameName;
    private double score;
    private LocalDateTime updateTime;

}
