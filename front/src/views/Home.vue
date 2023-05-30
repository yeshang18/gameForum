<template>
    <div class="home">
        <div class="top">
            <!-- <el-row :gutter="20">
                <el-col :span="4"><div class="grid-content ep-bg-purple" />
                    <el-text class="mx-1" type="primary" size="large">GameBBS</el-text>
                </el-col>
                <el-col :span="16"><div class="grid-content ep-bg-purple" /></el-col>
                <el-col :span="4"><div class="grid-content ep-bg-purple" />
                    <el-button type="primary">注册</el-button>
                    <el-button type="primary">登陆</el-button>
                </el-col>
            </el-row> -->
            <div class="logo">
                <div class="logo-text">GameBBS</div>
            </div>
            <div class="top-text">
                <div class="menu">
                    <a class="menu" href="#"><RouterLink to="/index">首页</RouterLink></a>
                    <a class="menu" href="#"><RouterLink to="/">论坛</RouterLink></a>
                    <a class="menu" href="#"><RouterLink to="/feedback">反馈</RouterLink></a>
                    <a class="menu" href="#" v-if="userInfo.power == 1"><RouterLink to="/end">管理</RouterLink></a>
                </div>
            </div>
            <div class="option">
                <el-button type="primary" @click="newPost()">发帖</el-button>
            </div>

            <!-- <div class="user-info">
                <el-dropdown>
                    <span class="el-dropdown-link">
                    Dropdown List
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                    </span>
                    <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>Action 1</el-dropdown-item>
                        <el-dropdown-item>Action 2</el-dropdown-item>
                        <el-dropdown-item>Action 3</el-dropdown-item>
                        <el-dropdown-item disabled>Action 4</el-dropdown-item>
                        <el-dropdown-item divided>Action 5</el-dropdown-item>
                    </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div> -->
            <div class="user-info" v-if="userInfo.id">
                <el-dropdown>
                    <avatar :userId="userInfo.id" style="{outline: none;}"></avatar>
                    <template #dropdown>
                        <div class="user-details" :style="{textAlign:'center'}">
                            <div class="user-name" :style="{'font-size':'14px','font-weight':'bold'}">{{userInfo.name}}</div>
                            <div class="user-id" :style="{'font-size':'12px','font-weight':'bold'}">uid:{{userInfo.id}}</div>
                            <div :style="{color:proxy.Util.level(userInfo.level).color,'font-size':'13px','font-weight':'bold'}" class="user-level">{{ proxy.Util.level(userInfo.level).info }}</div>
                            <div class="user-integral" :style="{'font-size':'12px','font-weight':'bold'}">积分:{{userInfo.integral}}</div>
                        </div>
                    <el-dropdown-menu>
                        <el-dropdown-item><RouterLink :to="'/user/'+userInfo.id">个人中心</RouterLink></el-dropdown-item>
                        <el-dropdown-item>帖子管理</el-dropdown-item>
                        <el-dropdown-item divided @click="loginOut()">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
            <div class="userButton" v-else>
                <div class="button-text">
                <el-button type="primary" @click="loginAndRegister(1)">注册</el-button>
                <el-button type="primary" @click="loginAndRegister(0)">登陆</el-button>
                </div>
            </div>
        </div>
        <div class="body-content">
            <RouterView />
        </div>
        <div>
            <LoginAndRegister ref="loginRegisterRef"></LoginAndRegister>
        </div>
    </div>
</template>

<script setup>
import LoginAndRegister from './LoginAndRegister.vue';
import { ref,onMounted,watch,getCurrentInstance } from 'vue';
import { useStore } from 'vuex';
import { getUserByIdApi,getGameByTypeApi,getGameApi,getPlatformApi,getGameTypeApi } from "@/api";
import router from '@/router';

const {proxy} = getCurrentInstance();
const store =useStore();

const loginRegisterRef =ref();
const loginAndRegister = (type)=>{
    loginRegisterRef.value.showPanel(type);
}



onMounted(()=>{
    // initScroll();
    getUserInfo();
});
//获取用户信息
const getUserInfo =async()=>{
    if(proxy.VueCookies.isKey("loginInfo")){
        let id = proxy.VueCookies.get("loginInfo").id;
        
    await getUserByIdApi(id).then(res=>{
            const data= res.data;
            if(data.code == 200){
                store.commit("updateLoginUserInfo",data.data)
            }
            else{
                return;
            }
        })
    }
    else{
        return;
    }
}



//退出登陆
const loginOut =() =>{
    proxy.VueCookies.remove("loginInfo");
    localStorage.removeItem("token");
    store.commit("updateLoginUserInfo",null);
    location.reload();
}

const userInfo =ref({})
//监听用户信息
watch(() => store.state.loginUserInfo,
(newVal, oldVal) => {
    if(newVal!=undefined&&newVal!=null){
        userInfo.value=newVal;
    }else{
        userInfo.value={};
    }
}, 
{ immediate: true, deep: true }
);
//监听登陆窗口
watch(() => store.state.showLogin,
(newVal, oldVal) => {
    if(newVal){
        loginAndRegister(0);
    }
  
}, 
{ immediate: true, deep: true }
);
//发帖页面
const newPost = ()=>{
    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    }
    else{
        router.push("/newArticle")
    }
}

</script>

<style scoped lang="scss">
.top{
    top: 0%;
    margin: 0 0;
    padding:0 0;
    width: 100%;
    height: 60px;
    opacity: 1;
    z-index: 999;
    background-color: white;
    position: fixed;
    display: flex;
    align-items: center;
    box-shadow: 0 2px 6px 0 #ddd;
    .top-text{
        width: 70%;
        display: flex;
        align-items: center;
        .menu{
            display: flex;
            margin: 10px;
        }

    }
    .option{
        padding-right: 10px;
        width: 10%;
        text-align: right;
    }
    .userButton{
        width: 10%;        
        //align-items: center;
        display:flex;
        // text-align: center;
        .button-text{
            display: flex;
            margin: auto;
            align-items: center;
        }
    }
    .logo{
        display: flex;
        width: 10%;
        // align-items: center;
        // text-align: center;
        .logo-text{
            margin:auto auto;
            align-items: center;
        }
    }
    .user-info{
        cursor: pointer;
        color: var(--el-color-primary);
        display: flex;
        align-items: center;
        outline: none;
    }
    
}
.body-content{
    margin-top: 60px;
}

</style>