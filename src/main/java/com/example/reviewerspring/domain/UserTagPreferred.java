package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "USER_TAG_PREFFERD_TB")
@IdClass(UserTagPreferred.class)
@Getter @Setter
public class UserTagPreferred implements Serializable {
    @Id
    @Column(name = "USER_PK")
    private Integer userPk;

    @Id
    @Column(name = "TAG_PK")
    private Integer tagPk;

    @ManyToOne
    @JoinColumn(name = "USER_PK", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "TAG_PK", insertable = false, updatable = false)
    private Tag tag;
}
