package com.example.reviewerspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String userId;
    private String password;
}
