package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "keywords")
@Getter @Setter
public class Keywords {
    @Id
    private String id;

    private Integer app_id;
    private Integer count;
    private String keyword;
    private String sentiment;
}
