package com.example.reviewerspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tag")
@Getter @Setter
public class Tag {
    @Id
    @Column(name = "TAG_PK")
    private Integer id;

    private String category;
    private String value;
}