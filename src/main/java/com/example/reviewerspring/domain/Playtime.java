package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playtime")
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