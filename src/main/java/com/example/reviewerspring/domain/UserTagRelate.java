package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "user_tag_relate")
@IdClass(UserTagRelate.class)
@Getter @Setter
public class UserTagRelate implements Serializable {
    @Id
    @Column(name = "USER_PK")
    private Integer userPk;

    @Id
    @Column(name = "TAG_PK")
    private Integer tagPk;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "USER_PK", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "TAG_PK", insertable = false, updatable = false)
    private Tag tag;
}