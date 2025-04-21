package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "updates")
@Getter
@Setter
public class Update {
    @Id
    @Column(name = "GAME_PK")
    private Integer gamePk;

    @OneToOne
    @MapsId
    @JoinColumn(name = "GAME_PK")
    private Game game;

    @Column(name = "Field")
    private LocalDateTime updateDate;
}