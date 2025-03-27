package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_WISHLIST_TB")
@IdClass(UserWishlist.class)
@Getter
@Setter
public class UserWishlist {
    @Id
    @Column(name = "USER_PK")
    private Integer userPk;

    @Id
    @Column(name = "GAME_PK")
    private Integer gamePk;

    @ManyToOne
    @JoinColumn(name = "USER_PK", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "GAME_PK", insertable = false, updatable = false)
    private Game game;
}
