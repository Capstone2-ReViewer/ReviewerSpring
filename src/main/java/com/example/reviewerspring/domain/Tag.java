package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TAGS_TB")
@Getter @Setter
public class Tag {
    @Id
    @Column(name = "TAG_PK")
    private Integer id;

    private String category;
    private String value;
}