package com.example.reviewerspring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserSignupRequest {
    private String userId;
    private String password;
    private String passwordCheck;

    private String name;
    private String nickname;
    private Integer age;
    private String gender;

    private List<Integer> preferredTags;    // tagId list
    private List<Integer> dislikedTags;     // tagId list
}
