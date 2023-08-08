package com.ssafy.game.game.db.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GameSession {
    private String sessionId;
    private Map<String,GameMember> gameMembers;
    private Map<String,Topic> topics;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.gameMembers = new HashMap<>();
        this.topics = new HashMap<>();
    }

    public void pickTopic(String memberId, Integer type, String keyword){
        if(topics.containsKey(memberId)){
            topics.put(memberId,new Topic(type,keyword));
        }else{
            topics.get(memberId).setTopic(type, keyword);
        }
    }
}
