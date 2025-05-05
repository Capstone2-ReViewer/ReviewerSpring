package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter @Setter
public class GameTag implements Serializable {
    private Integer gamePk;
    private Integer tagPk;

    private Boolean isPopular;
}
