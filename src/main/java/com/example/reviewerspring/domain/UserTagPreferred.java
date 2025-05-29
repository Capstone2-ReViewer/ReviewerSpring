package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "userTagPreferred")
public class UserTagPreferred implements Serializable {
    @Id
    private String id;

    private String userId;
    private Integer tagId;
    private String tagName;

    private boolean isPreferred;  // true면 선호, false면 비선호
}

