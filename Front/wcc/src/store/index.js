import { createStore } from "vuex";

export default createStore({
  namespaced: true,
  state: {
    userInfo: {
      id: "",
      email: "",
      password: "",
      nickname: "",
      point: "",
      money: "",
      admin: "", // 관리자: 0, 유저: 1
      suspension_day: "",
      Current_login: "",
      profile_img: "",
    },
    accessToken: null,
    refreshToken: null,
    isValidToken: false,
    mainStreamManager: '',
    client:null,
  },
  mutations: {
    setClient(state, payload){
      state.client = payload;
    },
    setAccessToken(state, payload) {
      state.accessToken = payload;
    },
    initUserInfo(state){
      state.userInfo = {
        id: "",
        email: "",
        password: "",
        nickname: "",
        point: "",
        money: "",
        admin: "", // 관리자: 0, 유저: 1
        suspension_day: "",
        current_login: "",
        profile_img: "",
      };
    },
    setUserEmail(state, payload){
      state.userInfo.email = payload;
    },
    setUserMoney(state, payload){
      state.userInfo.money = payload;
    },
    setUserNickname(state, payload){
      state.userInfo.nickname = payload;
    },
    setUserPoint(state, payload){
      state.userInfo.point = payload;
    }
  },

  getters: {
    getUserInfo(state) {
      console.log("getuserinfo called............")
      console.log("userInfo: ", state.userInfo);
      return state.userInfo;
    },
    getAccessToken(state) {
      return state.accessToken;
    },
    getClient(state){
      return state.client;
    }
  },

  actions: {},
});
