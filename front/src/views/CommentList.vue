<template>
  <div class="comment-body">
    <div class="comment-title">
        <div class="title">
            评论<span class="count">{{ comments.total }}</span>
        </div>
        <div class="tab">
            <span @click="getCommentByArticle(articleId,1)" class="tab-text" :style="{color:orderType==1?'#2366ff':''}">热榜</span>
            <el-divider direction="vertical"></el-divider>
            <span @click="getCommentByArticle(articleId,2)" class="tab-text" :style="{color:orderType==2?'#2366ff':''}">最新</span>
        </div>
    </div>
   <div class="comment-form-panel">
        <PostComment :avatar-width="50" 
        :user-id="currentUserInfo.id" 
        :user-img="currentUserInfo.img"
        :article-id="articleId" 
        :placeholder-info="'快发送一条友善的评论吧~'"
        :comment-type="1"
        :replyUserId="articleUserId"
        @post-finish="postFinishHandle"></PostComment>
    </div>
   <div class="comment-list">
    <DataList :dataSource="comments">
        <template #default="{data}" @loadData="nextPage">
            <CommentListItem :commentData="data" 
            :articleUserId="articleUserId"
            :current-user-img="currentUserInfo.img"
            :current-user-id="currentUserInfo.id"
            @hiddenAllReply="hiddenAllReplyHandle"
            @deleteLoadHandle="deleteLoadHandle"></CommentListItem>
        </template>
    </DataList>
   </div>
  </div>
</template>

<script setup>
import PostComment from "./PostComment.vue";
import CommentListItem from "./CommentListItem.vue";
import { ref, getCurrentInstance,watch } from "vue"
import { useStore } from "vuex";
import { getCommentByArticleApi } from "@/api";


const { proxy } = getCurrentInstance();

const store = useStore();
const props = defineProps({
    articleId:{
        type:Number,
    },
    articleUserId:{
        type:Number
    }
})

//form
const formData = ref({});
const formDataRef = ref();
const rules = {
    content:[{required: true,message:"请输入评论内容",trigger:"blur"},],
};



//用户信息
const currentUserInfo = ref({});
watch(() =>store.state.loginUserInfo,
 (newVal, oldVal) => {
    currentUserInfo.value=newVal||{};
 }, { immediate: true, deep: true });

const orderType =ref(1);

const nowPageNo =ref();
const nowPageSize =ref();

const nextPage=(pageNo,pageSize)=>{
    nowPageNo.value=pageNo;
    nowPageSize.value=pageSize;
    getCommentByArticle(props.articleId,orderType.value,pageNo,pageSize)
}



const comments =ref({
})

const deleteLoadHandle=()=>{
    getCommentByArticle(props.articleId,orderType.value,nowPageNo.value,nowPageSize.value)
}

//获取评论
const getCommentByArticle=(articleId,order,pageNo,pageSize)=>{
    orderType.value = order
    getCommentByArticleApi(articleId,order,pageNo,pageSize).then(res=>{
        const data= res.data;
        if(data.code==200){
            comments.value = data.data;
        }
    })
}
getCommentByArticle(props.articleId,orderType.value);

//隐藏所有回复框
const hiddenAllReplyHandle=()=>{
    comments.value.records.forEach(item =>{
        item.showReply=false;
    })
}

const postFinishHandle = (resultData)=>{
    comments.value.records.unshift(resultData);
}
</script>

<style lang="scss">
.comment-body{
    .comment-title{
        display: flex;
        align-items: center;
        .title{
            font-size: 20px;
            .count{
                font-size: 14px;
                padding: 0px 10px;
            }
        }
        .tab{
            .tab-text{
                cursor: pointer;
        }
        }
    }
    .comment-form-panel{
        margin-top: 20px;
    }
}
</style>
