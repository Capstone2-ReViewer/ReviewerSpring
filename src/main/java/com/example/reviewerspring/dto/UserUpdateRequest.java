package com.example.reviewerspring.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UserUpdateRequest {
    private String userId;
    private String password;
    private String passwordCheck;
    private String name;
    private String nickname;
    private Integer age;
    private String gender;

    private List<TagInfo> preferredTags;  // 쉼표로 구분된 문자열로 받음
    private List<TagInfo> dislikedTags;   // 쉼표로 구분된 문자열로 받음

    @Getter
    @Setter
    public static class TagInfo {
        private Integer tagId;
        private String tagName;
    }
}