package com.example.reviewerspring.api;

import com.example.reviewerspring.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameInfoAPI {
    //service에서 받아온 값을 호출온곳에 전송 -> controller(react로 개발)
    private final GameService gameService;


}
