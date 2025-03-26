package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GAME_TB")
@Getter @Setter
public class Game {
    @Id
    @Column(name = "GAME_PK")
    private Integer id;
//  웹 크롤링한다고 했지않나?
//    private String title;
//    private String poster;
//    private String synopsis;
//    private String genre;
//    private String dev;
//    private String publisher;

    @Column(name = "launch_date")
    private String launchDate;

    @OneToOne(mappedBy = "game")
    private GameScore score;

    @OneToOne(mappedBy = "game")
    private Playtime playtime;

    @OneToOne(mappedBy = "game")
    private Update update;

    @OneToMany(mappedBy = "game")
    private List<GameTag> tags;
}