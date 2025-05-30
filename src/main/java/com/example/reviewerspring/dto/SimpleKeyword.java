package com.example.reviewerspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleKeyword {
    private String keyword;
    private Integer count;

    public SimpleKeyword(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }
}
