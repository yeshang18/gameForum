<template>
  <div class="container-body" :style="{ width: '1000px'}">
    <div class="detail-info">
        <div class="article-detail">
            <div class="title"><h1>{{ articleInfo.title }}</h1></div>
            <div class="main-info">
                <div class="user-info">
                    <Avatar :width="40" :userId="articleInfo.userId"></Avatar>
                <div class="user-name"><RouterLink :to="'/user/'+articleInfo.userId">{{ articleInfo.userName }}</RouterLink> </div>
                <div class="post-time">{{ articleInfo.createTime }}</div>
                <div class="iconfont icon-eye-solid">{{ articleInfo.view == 0?"浏览":articleInfo.view }}</div>
                </div>               
                <div class="type-info">{{ articleInfo.gameName }} - {{ articleInfo.typeName }}</div>
            </div>
            <el-divider />
            <div class="content" v-html="articleInfo.content"></div>
        </div>
    </div>
     <!-- 评论 -->
    <div class="comment" id="view-comment">
        <CommentList 
        v-if="articleInfo.id"
        :articleId="articleInfo.id"
         :articleUserId="articleInfo.userId">
        </CommentList>
    </div>
  </div>
  <!-- 快捷操作 -->
  <div class="quick-panel" :style="{right:quickPanelLeft + 'px'}">
    <!-- 点赞 -->
        <el-badge :value="articleInfo.likes" type="info" :hidden="!articleInfo.likes>0">
            <div class="quick-item">
                <span class="iconfont icon-good" :style="{color:articleInfo.flag?'red':''}" @click="doLikeHandle(articleInfo.id)"></span>
            </div>
        </el-badge>
        <!-- 评论 -->
        <el-badge :value="articleInfo.comments" type="info" :hidden="!articleInfo.comments>0">
            <div class="quick-item">
                <span class="iconfont icon-comment" @click="goToPostion('view-comment')"></span>
            </div>
        </el-badge>
    </div>
</template>

<script setup>
import CommentList from "./CommentList.vue"
import { ref, getCurrentInstance, nextTick,onMounted } from "vue"
import { getArticleByIdApi,saveLikeApi,deleteLikeApi,saveViewApi } from "@/api";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const { proxy } = getCurrentInstance();
const store = useStore();

const route =useRoute();

const quickPanelLeft = (window.innerWidth-1000)/2-150;

const articleInfo =ref({});
const getArticleById = (articleId)=>{
    getArticleByIdApi(articleId).then(res=>{
        const data = res.data;
        if(data.code =200){
            articleInfo.value=data.data;
        }
    })
}

onMounted(()=>{
    getArticleById(route.params.articleId)
    setTimeout(() => {
        postViewHandle(route.params.articleId)
    }, 10*1000);
})

const postViewHandle=(id)=>{
    saveViewApi({articleId:id}).then(res=>{
        const data = res.data;
        if(data.code==200){
            return;
        }
        else{
            proxy.Message.error("系统繁忙");
        }
    })
}



const goToPostion= (domId)=>{
    const toTop =  document.querySelector("#"+domId).scrollHeight;
    document.documentElement.scrollTop=document.documentElement.offsetHeight-toTop-60;
    //document.querySelector("#"+domId).scrollIntoView();
}

const doLikeHandle=(id)=>{
    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    }

    let data = ref({});
    // if(type==1){
        data.value={articleId:id}
    // }
    // else if(type==0){
    //     data.value={commentId:id}
    // }
    if(!articleInfo.value.flag){
        saveLikeApi(data.value).then(res=>{
            const data = res.data;
            if(data.code==200)
            {
                articleInfo.value.flag=true;
                articleInfo.value.likes++;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else{
        deleteLikeApi(data.value).then(res=>{
            const data = res.data;
            if(data.code==200)
            {
                articleInfo.value.flag=false;
                articleInfo.value.likes--;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
}
</script>

<style lang="scss">
    .container-body{
        margin: 0 auto;
        margin-top: 70px;

        .detail-info{
            border-radius: 5px;
            padding: 16px 24px;
            background: #fff;
            
            .title{
                margin-bottom: 3px;
            }
            .main-info{
                .user-info{
                    display: flex;
                    align-items: center;
                    .user-name{
                        margin-left: 5px;
                    }
                    .post-time{
                        margin-left: 20px;
                        color: darkgrey;
                    }
                    .iconfont{
                        margin-left: 15px;
                        color: darkgrey;
                    }
                    .iconfont::before{
                        padding-right: 3px;
                    }
                }
               .type-info{
                margin-top: 5px;
                margin-left: 40px;
                color:brown;
               }
            }
            .content{
                img{
                    max-width: 90%;
                    cursor: pointer;
                }
                a{
                    color: #2366ff;
                }
            }
        }
        .comment{
            border-radius: 5px;
            margin-top: 10px;
            padding: 16px 24px;
            background: #fff;
        }
    }
    .quick-panel{
        position: fixed;
        top: 200px;
        width: 50px;
        text-align: center;
        .el-badge__content.is-fixed{
            top: 5px;
            right: 15px;
        }
        .quick-item{
            width: 50px;
            height: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 50%;
            background: #fff;
            margin-bottom: 30px;
            .iconfont{
                font-size: 26px;
                color:cadetblue;
                cursor: pointer;
            }
        }
    }
</style>
