package com.example.reviewerspring.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class UserUpdateRequest {
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;
    private List<Integer> preferredTags;
    private List<Integer> dislikedTags;
}