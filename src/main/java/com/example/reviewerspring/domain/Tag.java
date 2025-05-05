package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tag")
@Getter @Setter
public class Tag {
    @Id
    private Integer id;

    private String category;
    private String value;
}
