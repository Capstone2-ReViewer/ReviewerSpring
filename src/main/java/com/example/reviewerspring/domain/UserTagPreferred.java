package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class UserTagPreferred implements Serializable {
    private String userId;
    private Integer tagId;
}
