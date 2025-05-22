package com.example.reviewerspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "userWishlist")
public class UserWishlist {
    @Id
    private String id;

    private String userId;
    private String gameId;
}
