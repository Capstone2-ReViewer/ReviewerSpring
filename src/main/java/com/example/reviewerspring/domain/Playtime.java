package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PLAYTIME_TB")
@Getter @Setter
public class Playtime {
    @Id
    @Column(name = "GAME_PK")
    private Integer gamePk;

    @OneToOne
    @MapsId
    @JoinColumn(name = "GAME_PK")
    private Game game;

    private Integer avg;
    private Integer top10per;
}