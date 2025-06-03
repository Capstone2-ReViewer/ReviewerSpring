package com.example.reviewerspring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserUpdateRequest {
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;
    private String preferredTags;
    private String dislikedTags;

//    @Getter
//    @Setter
//    public static class TagInfo {
//        private Integer tagId;
//        private String tagName;
//    }
}