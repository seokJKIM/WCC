package com.ssafy.game.match.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameCreatedResponse {
    private boolean isSuccess;
    private String sessionId;
    private String screenToken;
    private String memberToken;
    private String memberId;
    private int matchStatus;

    public GameCreatedResponse(String sessionId, String screenToken, String memberId, String memberToken) {
        this.sessionId = sessionId;
        this.screenToken = screenToken;
        this.memberToken = memberToken;
        this.memberId = memberId;
        this.matchStatus = MatchStatus.CREATED;
        this.isSuccess = true;
    }
}
