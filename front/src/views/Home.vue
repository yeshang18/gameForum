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
                    <a class="menu" href="#">首页</a>
                    <a class="menu" href="#">论坛</a>
                    <a class="menu" href="#">反馈</a>
                </div>
            </div>
            <div class="userButton">
                <div class="button-text">
                <el-button type="primary" @click="loginAndRegister(1)">注册</el-button>
                <el-button type="primary" @click="loginAndRegister(0)">登陆</el-button>
                </div>
            </div>
        </div>
        <div>
            <LoginAndRegister ref="loginRegisterRef"></LoginAndRegister>
        </div>
    </div>
</template>

<script setup>
import LoginAndRegister from './LoginAndRegister.vue';
import { ref } from 'vue';

const showDiglog = ref(true)

const loginRegisterRef =ref();
const loginAndRegister = (type)=>{
    loginRegisterRef.value.showPanel(type);
}



</script>

<style scoped lang="scss">
    .top{
        margin: 0 0;
        padding:0 0;
        width: 100%;
        height: 60px;
        position: fixed;
        display: flex;
        box-shadow: 0 2px 6px 0 #ddd;
        .top-text{
            width: 80%;
            display: flex;
            align-items: center;
            .menu{
                display: flex;
                margin: 10px;
            }

        }
        .userButton{
            width: 10%;        
            //align-items: center;
            display:flex;
            // text-align: center;
            .button-text{
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
    }

</style>