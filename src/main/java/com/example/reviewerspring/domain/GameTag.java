package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "game_tag")
@IdClass(GameTag.class)
@Getter @Setter
public class GameTag implements Serializable {
    @Id
    @Column(name = "GAME_PK")
    private Integer gamePk;

    @Id
    @Column(name = "TAG_PK")
    private Integer tagPk;

    @Column(name = "is_popular")
    private Boolean isPopular;

    @ManyToOne
    @JoinColumn(name = "GAME_PK", insertable = false, updatable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "TAG_PK", insertable = false, updatable = false)
    private Tag tag;
}