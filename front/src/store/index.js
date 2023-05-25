import { createStore } from 'vuex'

export default createStore({
  state: {
    loginUserInfo:null,
    showLogin:false,
    // tableInfo:null,
    // gameInfo:null,
    // platformInfo:null,
    // gameTypeInfo:null,
    updateFlag:0,
  },
  getters: {
    getLoginUserInfo:(state)=>{
      return state.loginUserInfo;
    },
    // getTableInfo:(state)=>{
    //   return state.tableInfo;
    // }
    // getgGmeInfo:(state)=>{
    //   return state.gameInfo;
    // },
    // getPlatformInfo:(state)=>{
    //   return state.loginUserInfo;
    // },
    // getGameTypeInfo:(state)=>{
    //   return state.loginUserInfo;
    // },
  },
  mutations: {
    updateLoginUserInfo(state,value){
      state.loginUserInfo=value;
    },
    showLogin(state,value){
      state.showLogin=value;
    },
    setUpdateFlag(state,value){
      state.updateFlag=value;
    }
    // updateGameInfo(state,value){
    //   state.gameInfo=value;
    // },
    // updatePlatformInfo(state,value){
    //   state.platformInfo=value;
    // },
    // updateGameTypeInfo(state,value){
    //   state.gameTypeInfo=value;
    // },
    // updateTableInfo(state,value){
    //   state.tableInfo=value;
    // },
  },
  actions: {
  },
  modules: {
  }
})
