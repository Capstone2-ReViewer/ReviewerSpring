package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_TB")
@Getter @Setter
public class User {
    @Id
    @Column(name = "USER_PK")
    private Integer id;

    private String userId;
    private String password;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;
}