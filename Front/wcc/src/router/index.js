import LoginViewVue from "@/views/Member/LoginView.vue";
import FindPasswordViewVue from "@/views/Member/FindPasswordView.vue";
import NicknameViewVue from "@/views/Member/NicknameView.vue";
import NicknameChangeViewVue from "@/views/Member/NicknameChangeView.vue";
import PwChangeViewVue from "@/views/Member/PwChangeView.vue";
import SignupViewVue from "@/views/Member/SignupView.vue";
import WelcomeViewVue from "@/views/Member/WelcomeView.vue";
import GoodbyeViewVue from "@/views/Member/GoodbyeView.vue";

import HomeView from "@/views/home/HomeView.vue"

import GameRoomVue from "@/views/gameroom/GameRoom.vue";
import GamePrepareVue from "@/views/gameroom/prepare/GamePrepare.vue";
// import MainGameVue from "@/views/gameroom/maingame/MainGame.vue";
import GameResultVue from "@/views/gameroom/gameresult/GameResult.vue"

import { createWebHistory, createRouter } from "vue-router";

// 유효성 검증 코드 참조

// const onlyAuthUser = async (to, from, next) => {
//   const checkUserInfo = store.getters["memberStore/checkUserInfo"];
//   const checkToken = store.getters["memberStore/checkToken"];
//   let token = sessionStorage.getItem("access-token");
//   console.log("로그인 처리 전", checkUserInfo, token);

//   if (checkUserInfo != null && token) {
//     console.log("토큰 유효성 체크하러 가자!!!!");
//     await store.dispatch("memberStore/getUserInfo", token);
//   }
//   if (!checkToken || checkUserInfo === null) {
//     alert("로그인이 필요한 페이지입니다..");
//     // next({ name: "login" });
//     router.push({ name: "home" });
//   } else {
//     console.log("로그인 했다!!!!!!!!!!!!!.");
//     next();
//   }
// };

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginViewVue,
  },
  {
    path: "/welcome",
    name: "welcome", 
    component: WelcomeViewVue,
  },
  {
    path: "/goodbye",
    name: "goodbye", 
    component: GoodbyeViewVue,
  },
  {
    path: "/findpw",
    name: "findpw",
    component: FindPasswordViewVue,
  },
  {
    path: "/nickname",
    name: "nickname",
    component: NicknameViewVue,
  },
  {
    path: "/nicknamechange",
    name: "nicknamechange",
    component: NicknameChangeViewVue,
  },
  {
    path: "/pwchange",
    name: "pwchange",
    component: PwChangeViewVue,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignupViewVue,
  },

  {
    path: "/home",
    name: "homeview",
    component: HomeView,
  },

  {
    path: "/gameroom",
    name: "gameroom",
    component: GameRoomVue,
  },
  {
    path: "/gameprepare",
    name: "gameprepare",
    component: GamePrepareVue,
  },
  // {
  //   path: "/maingame",
  //   name: "maingame",
  //   component: MainGameVue,
  // },
  {
    path: "/gameresult",
    name: "gameresult",
    component: GameResultVue,
  },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
