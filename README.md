# 😄Woot Cham Club
<img src="Docs/image/login.png" width="80%">

# 📃목차

1. [프로젝트소개](#프로젝트-소개)
2. [개발기간](#개발-기간)
3. [설계](#설계)
4. [주요기능(캡쳐)](#주요-기능)
5. [시연화면](#시연-화면)
6. [개발환경](#개발-환경)
7. [프로젝트 회고](#프로젝트-회고)
8. [팀원소개](#멤버-구성)

# 💡프로젝트 소개

### 서비스 소개
- 화상채팅을 통해 비대면으로 게임을 진행하고, 웃은 횟수에 따라 점수를 부여해 서로 경쟁하는 게임 플랫폼입니다.


### 서비스 특징
- 웹캠과 WebRTC기술을 활용해 비대면으로 여러명이 실시간으로 게임에 참여 가능

- 사용자의 얼굴 변화를 체크해 웃음 횟수를 체크하고 이를 결과에 반영함

### 기획 배경
- 최근 유튜브에서 많은 인기를 얻고 있는 메타코미디 클럽, 유병재의 생일파티, 조충현의 웃으면 강퇴 등의 컨텐츠 들의 공통점인 `웃음참기`에 초점을 맞춤

- 웃음을 참는 것으로 재미를 얻을 수 있다는 속성 반영

- 간단하게 즐길 수 있는 게임에 이를 적용

<br>


# 📆개발 기간
* 23.07.10일 - 23.08.18일

<br>

# 💡설계

### 1. ERD
<img src="Docs/image/ERD.png" width="80%">

### 2. 시스템 아키텍처
<img src="Docs/image/System Architecture.png" width="80%">

<br>

# ✨주요 기능

### 1. 웃음 참기 게임
- 메인화면에서 카메라, 마이크 설정을 통해 시작하기 버튼 활성화

- 시작하기 버튼을 통한 게임 매칭 실행

- 5인 매칭 성공 시 게임룸으로 이동

- 게임 룰
    1. 미션 선택 후 해당 미션에 따른 개그 진행

    2. 웃을 시 컴포넌트에 이펙트가 발생하며 카운트가 늘어남

    3. 발표자는 카운트 되지 않음

    4. 입을 가려 인식이 되지 않으면 5초에 1번씩 카운트가 올라감

    5.  화면공유를 통해 영상 매체 활용 가능, 채팅 가능

    6. 결과가 끝나면 카운트 된 웃음 횟수별로 순위 지정 및 점수 반영

### 2. OpenVidu를  화상 통화 & 채팅
- 게임을 진행하기 위해 매칭이 잡힌 유저끼리 화상 채팅을 연결함
- 채팅을 통해 서로 소통할 수 있음

### 3. FaceAPI를 활용한 웃음 캐치
- 화면의 변화를 FaceAPI를 활용해 웃은 횟수를 체크해서 최종 순위에 반영함
- 얼굴이 인식되지 않는 경우 5초를 기준으로 카운트가 1회씩 증가함
- 얼굴을 가리고 웃거나 화면을 이탈하는 경우를 방지함

### 4. 도감 
- 게임을 통해 얻은 포인트로 프로필사진, 테두리, 이름표를 구매할 수 있음
- 구매한 아이템을 확인 할 수 있고, 아이템을 교체할 수 있음

### 5. 랭킹 
- 레이팅을 통해 유저들의 랭킹을 확인할 수 있음

<br>

# 🔫시연 화면

## **기본 UI**
#### 회원가입
![signup_1](/Docs/gif/signup_email.gif)
![email](/Docs/gif/signup_email_check.gif)
![signup_2](/Docs/gif/signup_password.gif)
![signpu_nickname_check](/Docs/gif/signup_nickname_check.gif)

#### 로그인
![login](/Docs/gif/login.gif)

#### 로그아웃
![logout](/Docs/gif/logout.gif)

#### 홈

#### 공지
![notice](/Docs/gif/notice_user.gif)

#### 도감
![collection](/Docs/gif/collection_no_money.gif)
![collection_list](/Docs/gif/collection_image.gif)
![collection_list](/Docs/gif/collection_fream.gif)

#### 랭크
![rank](/Docs/gif/rank.gif)

## **게임 UI**
#### 게임 실행
1. 매칭

![game_matching](/Docs/gif/game_matching.gif)

2. 매칭 수락

![game_accept](/Docs/gif/game_accept.gif)

3. 미션 선택

![game_choice](/Docs/gif/game_choice.gif)

4. 게임 시작

![game_first](/Docs/gif/game_first.gif)

5. 채팅

![game_chating](/Docs/gif/game_chating.gif)

6. 다음 순서 대기

![game_wait](/Docs/gif/game_wait.gif)

7. 게임 결과

![gmae](/Docs/gif/game_end.gif)


# 🛠️개발 환경

#### 💻 **IDE**
    - Intellij
    - Visual Studio Code

#### 🔧 **Backend**
    - Springboot 2.7.2
    - Java 11
    - Gradle 8.2.1
    - JPA
    - MySQL 8.1.0
    - Swagger
    - WebSocket

#### 🎨 **Frontend**
    - HTML, CSS
    - JavaScript
    - Vue 3.2.37
    - Node.js 16.16.0

#### 🚀 **배포**
    - AWS EC2
    - Ubuntu 20.04
    - Jenkins
    - Docker 20.10.17

#### 📊 **버전/이슈 관리**
    - Jira
    - GitLab

#### 🔨 **Tool**
    - Postman
    - Figma

#### 🤝 **협업**
    - Mattermost
    - Notion

# ✏️프로젝트 회고

 ### JWT와 Redis의 사용
    - JWT를 DB에 저장하지 않고 Redis에 저장함
    - 토큰은 발급 후 일정 시간 이후 만료처리 해야하기 때문에 DB에 직접 저장하고 시간이 지나면 삭제하는 과정을 처리하기 위해선 스케줄러를 사용해 주기적으로 처리해야함
    - Redis는 기본적으로 데이터의 유효 기간을 지정할 수 있기 때문에 토큰 관리에 적합하다고 판단함
 ### BlackList 사용에 대한 의문
    - 로그아웃을 한 토큰을 구분하기 위해 BlackList로 등록하여 구분함
    - 이 때, redis에 저장된 토큰을 삭제하는것과 BlackList로 추가 등록하는것의 차이점에 대한 의문이 있었음
    - 직접 삭제와 블랙리스트 사용의 차이점은 상황에 따라 다르다는 결론을 내림.
    - 직접 삭제할 때의 장점은 단순성과 blacklist를 추가 조회하지 않기 때문에 성능부분에서 이점이 있음
    - BlackList를 사용하면 조금 더 높은 보안 수준을 제어하기 위해 사용하며 토큰 체크를 2중으로 한다는 느낌으로 받아드림

### Procedure의 사용에 대한 의문
    - 하나의 요청으로 여러 SQL문을 실행할 수 있기 때문에 네트워크 부하를 줄일 수 있다는 장점때문에 사용함
    - 그렇다면, 어떤 상황이 Procedure를 사용하기에 적합한지에 대한 의문을 가짐
    - 이 프로젝트에서 게임이 끝날 때 게임포인트와 랭킹포인트를 반영하는데 있어서 프로시저를 사용함
    - 게임포인트가 깎여서 음수가 될 경우 0점으로 처리함
    - 이 때, 이 과정을 Procedure로 등록하여 처리함 => 이 과정을 Procedure에서 처리하는게 과연 올바른 선택인지에 대한 의문을 가짐
    - 의문에 대한 해소를 하지 못함. 상황에 따라 많은 경우의 수가 있을 수 있음.
    - 하지만 위의 경우는 백단에서 처리를 한 후 하나의 쿼리 실행으로 변경하는 것이 맞다고 생각을 함 => 한 번의 쿼리 호출로 해결할 수 있기 때문

### 느낀점 
    - Infra에 관한 기본 지식이 필요하다는 생각을 함
    - Ec2 서버를 이용해 배포를 진행하였지만 이 부분에 대한 지식이 많이 부족하여 어려움을 겪음
    - Docker를 처음 접하면서 기본적인 개념을 이해하는데 도움이 됨
    - Spring Data JPA를 사용하되 queryDSL을 이용해 동적쿼리를 작성해보는 연습을 해보고싶다고 느낌
    - JPA의 N+1문제에 대한 이해도가 부족하다는 생각이 들었음. 의도하지 않는 추가적인 데이터 조회를 확인했으나 이를 해결하지 못함. 추후에 공부를 통해 해결해보고 싶음
    - 이번 프로젝트를 진행하면서 JPA, JWT, Redis, Docker 등 처음 적용해보는 기술들이 많아서, 이를 공부하고 적용해보면서 재미를 느낌. 결과물은 기대에 미치지 못했지만 개인적으론 많은 성장을 하게 된 프로젝트라고 생각함

# 👨‍👨‍👦‍👧멤버 구성
|                                김석주                                 |                                박예한                                 |                                옥수빈                                 |                                왕준영                                 |                                이주용                                 |                                              임휘진                                              |
|:------------------------------------------------------------------:|:------------------------------------------------------------------:|:------------------------------------------------------------------:|:------------------------------------------------------------------:|:------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------:|
| <img src="Docs/image/김석주.jpg" width="80px;" height="80px" alt=""/> | <img src="Docs/image/박예한.jpg" width="80px;" height="80px" alt=""/> | <img src="Docs/image/옥수빈.jpg" width="80px;" height="80px" alt=""/> | <img src="Docs/image/왕준영.jpg" width="80px;" height="80px" alt=""/> | <img src="Docs/image/이주용.jpg" width="80px;" height="80px" alt=""/> | <img src="Docs/image/임휘진.png" width="80px;" height="80px" alt=""/> |
|                      Back-end<br/>Api Server                       |                   Back-end<br/>Infra<br/>                    |                           Front-end<br/>                           |                      Back-end<br/>Game Sever                       |                       Front-end<br/>                     |                                    Front-end<br/>                                  |

<br/>
