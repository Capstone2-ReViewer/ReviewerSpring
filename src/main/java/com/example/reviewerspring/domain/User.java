package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Getter @Setter
public class User {
    @Id
    private String id;

    private String userId;
    private String password;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;
}
