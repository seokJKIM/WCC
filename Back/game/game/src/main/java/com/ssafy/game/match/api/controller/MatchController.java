package com.ssafy.game.match.api.controller;

import com.ssafy.game.match.api.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @MessageMapping("/start")
    void start(){
        matchService.startMatch();
    }
}
