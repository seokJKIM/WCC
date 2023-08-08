package com.ssafy.game.match.api.controller;

import com.ssafy.game.match.api.observer.MatchEventSource;
import com.ssafy.game.match.api.observer.MatchObserver;
import com.ssafy.game.match.api.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(originPatterns = "*")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;

        MatchEventSource matchEventSource = new MatchEventSource(matchService);
        MatchObserver matchObserver = new MatchObserver(matchService);

        matchEventSource.addObserver(matchObserver);
        Thread thread = new Thread(matchEventSource);
        thread.start();
    }

    @SubscribeMapping("/user/queue/match")
    void setMatchService(@Header("simpSessionId") String memberId){
        System.out.println("memberId = " + memberId);
        matchService.createMatchMemberByMemberId(memberId);
    }


    @MessageMapping("/enter/{groupId}/{memberId}")
    void enter(@DestinationVariable String groupId,@DestinationVariable String memberId){
        matchService.enterGame(groupId, memberId);
    }

    @SubscribeMapping("/topic/test")
    @SendTo("/topic/test")
    String test(){
        return "test success";
    }
}
