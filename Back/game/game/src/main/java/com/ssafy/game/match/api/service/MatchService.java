package com.ssafy.game.match.api.service;

import com.ssafy.game.game.api.processor.GameProcessor;
import com.ssafy.game.game.db.entity.GameMember;
import com.ssafy.game.game.db.entity.GameSession;
import com.ssafy.game.game.db.repository.GameMemberRepository;
import com.ssafy.game.game.db.repository.GameSessionRepository;
import com.ssafy.game.match.api.response.GameCreatedResponse;
import com.ssafy.game.match.db.entity.Group;
import com.ssafy.game.match.db.repository.GroupRepository;
import com.ssafy.game.match.api.request.Member;
import com.ssafy.game.match.api.response.TimerResponse;
import com.ssafy.game.match.api.response.MatchResponse;
import com.ssafy.game.match.common.GameSetting;
import com.ssafy.game.match.api.response.MatchStatus;
import com.ssafy.game.match.db.repository.MatchMemberSession;
import com.ssafy.game.util.MessageSender;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchService {
    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    private Deque<Member> matchMemberQueue;
    private int count;
    private final MatchMemberSession matchMemberSession;
    private final GroupRepository groupRepository;
    private final MessageSender messageSender;
    private final GameSessionRepository gameSessionRepository;
    private final GameMemberRepository gameMemberRepository;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
        this.matchMemberQueue = new ArrayDeque<>();
        this.count = -1;
    }

    public void createMatchMemberByMemberId(String memberId){
        Member matchMember = new Member(memberId);

        this.matchMemberQueue.offer(matchMember);
        this.matchMemberSession.insertMember(matchMember);
    }

    public void deleteMatchMemberByMemberId(String memberId){
        this.matchMemberSession.deleteMemberByMemberId(memberId);
    }

    public boolean matchable(){
        System.out.println("this.matchMemberQueue.size() = " + this.matchMemberQueue.size());
        System.out.println("matchMemberQueue = " + matchMemberQueue);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(this.matchMemberQueue.size()<GameSetting.MAX_GAMEMEMBER_COUNT) return false;

        return true;
    }

    public void sendMatchResult(List<Member> groupMemberList){
        try{
            Group group = groupRepository.createNewGroup();
            System.out.println("send Match Result");
            sendMatchStatusToGroupMembers(groupMemberList, group,MatchStatus.MATCHED);

            int second = GameSetting.MAX_GAMEMEMBER_ENTER_WAIT_SECOND;
            while(second-->0){
                sendObjectToGroupMembers(
                        groupMemberList,
                        new TimerResponse(second)
                );

                /**
                 * ToDo
                 * openvidu 모듈화
                 * 게임 시작 모듈화 및 이름 수정
                 * 메세지 보내기 모듈화
                 * 게임 시작 스레드 관리
                 */
                if(group.getMembers().size() == GameSetting.MAX_GAMEMEMBER_COUNT){
                    SessionProperties gameSessionProperties = new SessionProperties.Builder().build();
                    Session openviduSession = openvidu.createSession(gameSessionProperties);
                    String gameSessionId = openviduSession.getSessionId();

                    GameSession gameSession = new GameSession(gameSessionId);
                    gameSessionRepository.insertGameSession(gameSession);
                    sendGameLoadRequestToAll(groupMemberList,openviduSession);

                    GameProcessor gameProcessor = new GameProcessor(gameSession,this.messageSender);
                    Thread thread = new Thread(gameProcessor);
                    thread.start();
                    return;
                }

                Thread.sleep(1000);
            }

            Collection<Member> enterGroupMemberList = group.getMembers().values();

            sendMatchStatusToGroupMembers(enterGroupMemberList, group,MatchStatus.MATCHING);

            for(Member member : group.getMembers().values()){
                if(member.isConnected()){
                    this.matchMemberQueue.offerFirst(member);
                }
            }
            groupRepository.deleteGroupByGroupId(group.getGroupId());
        }catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (OpenViduJavaClientException e) {
            throw new RuntimeException(e);
        } catch (OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Member> getGroupMemberList(){
        Member matchMember;
        List<Member> groupMemberList = new ArrayList<>(GameSetting.MAX_GAMEMEMBER_COUNT);

        while(!matchMemberQueue.isEmpty() && groupMemberList.size()<GameSetting.MAX_GAMEMEMBER_COUNT){
            matchMember = matchMemberQueue.poll();

            if(matchMember.isConnected()){
                groupMemberList.add(matchMember);
            }
        }

        if(groupMemberList.size() == GameSetting.MAX_GAMEMEMBER_COUNT){
            return groupMemberList;
        }

        for(Member cancelMember : groupMemberList){
            if(cancelMember.isConnected()){
                matchMemberQueue.offerFirst(cancelMember);
            }
        }

        return null;
    }

    public void enterGame(String groupId, String memberId){
        groupRepository.findGroupByGroupId(groupId)
                .getMembers()
                .put(memberId,matchMemberSession.findByMemberId(memberId));
    }

    private void sendObjectToGroupMembers(Collection<Member> groupMemberList, Object object){
        for(Member groupMember : groupMemberList){
            messageSender.sendObjectToMember(
                    groupMember.getMemberId(),
                    "/queue/match",
                    object
            );
        }
    }

    private void sendMatchStatusToGroupMembers(Collection<Member> groupMemberList, Group group, int matchStatus){
        for(Member groupMember : groupMemberList){
            messageSender.sendObjectToMember(
                    groupMember.getMemberId(),
                    "/queue/match",
                    new MatchResponse(
                            groupMember.getMemberId(),
                            group.getGroupId(),
                            matchStatus
                    )
            );
        }
    }

    private void sendGameLoadRequestToAll(Collection<Member> groupMemberList,Session openviduSession){
        try {
            for(Member groupMember : groupMemberList){
                ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                        .role(OpenViduRole.SUBSCRIBER)
                        .data(groupMember.getMemberId())
                        .build();
                Connection connection = openviduSession.createConnection(connectionProperties);

                GameMember gameMember = new GameMember(openviduSession.getSessionId(),groupMember.getMemberId(),connection.getToken());
                gameMemberRepository.save(gameMember);

                messageSender.sendObjectToMember(
                        gameMember.getMemberId(),
                        "/queue/match",
                        new GameCreatedResponse(
                                gameMember.getSessionId(),
                                gameMember.getMemberId(),
                                gameMember.getMemberToken()
                        )
                );
            }
        } catch (OpenViduJavaClientException e) {
            throw new RuntimeException(e);
        } catch (OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
    }

}
