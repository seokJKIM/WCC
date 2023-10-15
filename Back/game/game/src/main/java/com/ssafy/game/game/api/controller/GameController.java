package com.ssafy.game.game.api.controller;

import com.ssafy.game.game.api.request.SkipPickTopicRequest;
import com.ssafy.game.game.api.request.SkipPrepareRequest;
import com.ssafy.game.game.api.request.TopicRequest;
import com.ssafy.game.game.api.request.UpSmileCountRequest;
import com.ssafy.game.game.api.response.SmileResponse;
import com.ssafy.game.game.api.service.GameService;
import com.ssafy.game.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class GameController {

    private final GameService gameService;
    private final MessageSender sender;

    @SubscribeMapping("/topic/game/{sessionId}")
    void load(@DestinationVariable String sessionId, @Header("simpSessionId") String memberId){
        gameService.load(sessionId,memberId);
    }

    @MessageMapping("/pick/shuffle/{sessionId}")
    void pick(@Payload TopicRequest topicRequest, @DestinationVariable String sessionId){
        gameService.pick(sessionId,topicRequest);
    }

    @MessageMapping("/skip/pick")
    void skipPick(@Payload SkipPickTopicRequest skipPickTopicRequest){
        gameService.skipPickTopic(skipPickTopicRequest);
    }
    @MessageMapping("/skip/prepare/{sessionId}")
    void skipPrepare(@DestinationVariable String sessionId, @Payload SkipPrepareRequest skipPrepareRequest){
        gameService.skipPreparePresent(sessionId,skipPrepareRequest);
    }

    @MessageMapping("/up")
    void upSmileCount(@Payload UpSmileCountRequest upSmileCountRequest){
        gameService.upSmileCount(upSmileCountRequest);
        sender.sendObjectToAll(
                "/topic/game/"+upSmileCountRequest.getSessionId(),
                new SmileResponse(upSmileCountRequest.getMemberToken())
        );
    }

    @MessageMapping("/skip/present/{sessionId}")
    void skipPrepare(@DestinationVariable String sessionId,@Payload String memberToken){
        gameService.skipPresent(sessionId,memberToken);
    }

}
