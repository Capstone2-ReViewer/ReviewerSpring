package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playtime")
@Getter @Setter
public class Playtime {
    @Id
    private String id; // 게임 ID

    private Integer avg;
    private Integer top10per;
}
