package com.example.reviewerspring.Repository;

import com.example.reviewerspring.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository_Interface_ver extends JpaRepository<Game, String> {
    List<Game> findByTitleContaining(String title);
    List<Game> findAllByOrderByScoreDesc();
}
