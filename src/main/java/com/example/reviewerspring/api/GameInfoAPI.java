package com.example.reviewerspring.api;

import com.example.reviewerspring.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameInfoAPI {

    private final GameService gameService;


}
