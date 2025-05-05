package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "updates")
@Getter
@Setter
public class Update {
    @Id
    private String id; // 게임 ID

    private LocalDateTime updateDate;
}
