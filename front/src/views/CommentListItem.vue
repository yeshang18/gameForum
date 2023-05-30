<template>
  <div class="comment-item">
    <Avatar :width="50" :userId="commentData.userId"></Avatar>
    <div class="comment-info">
        <div class="user-name">
            <span class="name"><RouterLink :to="'/user/'+commentData.userId">{{ commentData.userName }}</RouterLink> </span>
            <span v-if="commentData.userId == articleUserId" class="tag-author">作者</span>
        </div>
        <div class="comment-content">
            <span v-if="commentData.isTop==1" class="top">置顶</span>
            <span v-html="commentData.content"></span>
        </div>
        <div class="op-panel">
            <div class="time">
                {{ commentData.createTime }}
            </div>
            <div class="iconfont icon-good" @click="likeClickHandle(commentData)" :style="{color:commentData.flag?'red':''}">
                {{ commentData.likes>0?commentData.likes:"点赞" }}</div>
            <div class="iconfont icon-comment" @click="showReplyHandle(commentData,0)">回复</div>
            <el-dropdown v-if="articleUserId==currentUserId||commentData.userId==currentUserId">
                <div class="iconfont icon-more"></div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item v-if="articleUserId==currentUserId">
                                <div @click="setTopHandle(commentData)">
                                    {{ commentData.isTop==0?"置顶":"取消置顶" }}
                                </div>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <div @click="deleteHandle(commentData.id)">删除评论</div>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
            </el-dropdown>
        </div>
        <!-- 二级评论 -->
        <div class="comment-sub-list" v-if = "commentData.children">
            <div class="comment-sub-item" v-for="childrenData in commentData.children">
                <Avatar :width="30" :userId="childrenData.userId"></Avatar>
                <div class="comment-info">
                    <div class="user-name">
                        <span class="name"><RouterLink :to="'/user/'+childrenData.userId">{{ childrenData.userName }}</RouterLink></span>
                        <span v-if="childrenData.userId==articleUserId" class="tag-author">作者</span>
                        <span>回复 </span>
                        <span class="reply-user">@{{ childrenData.replyUserName }}</span>
                        <span> :</span>
                        <span v-html="childrenData.content" class="reply-content"></span>
                    </div>
                    <div class="op-panel">
                    <div class="time">
                        {{ childrenData.createTime }}
                    </div>
                    <div class="iconfont icon-good" @click="likeClickHandle(childrenData)" :style="{color:childrenData.flag?'red':''}">
                        {{ childrenData.likes>0?childrenData.likes:"点赞" }}</div>
                    <div class="iconfont icon-comment" @click="showReplyHandle(childrenData,1)">回复</div>
                        <el-dropdown v-if="childrenData.userId==currentUserId">
                        <div class="iconfont icon-more"></div>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>
                                    <div @click="deleteHandle(childrenData.id)">删除评论</div>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                        </el-dropdown>
                    </div>
                </div>
            </div>
        </div>
        <div class="reply-info" v-if="commentData.showReply">
            <PostComment 
            :avatarWidth="30" 
            :userId="currentUserId" 
            :commentId="commentData.id"
            :placeholderInfo="'回复 @'+commentData.userName"
            :commentType="2"
            :replyUserId="replyUserId"
            @postFinish="postFinishHandle"></PostComment>
        </div>
    </div>
  </div>
</template>

<script setup>
import PostComment from './PostComment.vue';
import { ref, getCurrentInstance, onMounted} from "vue"
import {saveLikeApi,deleteLikeApi, deleteCommentApi, setTopCommentApi,unTopCommentApi } from "@/api";
import { useStore } from "vuex";
const { proxy } = getCurrentInstance();
const store = useStore();

const replyUserId = ref();
const placeholderInfo = ref();

const props = defineProps({
    commentData:{
        type:Object
    },
    articleUserId:{
        type:Number
    },
    currentUserId:{
        type:Number
    }
})


const emit = defineEmits(["hiddenAllReply","deleteLoadHandle"])
//显示评论框
const showReplyHandle = (curData,type)=>{

    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    } 


    let show =  props.commentData.showReply==undefined?false:props.commentData.showReply;
    emit("hiddenAllReply");
    if(type==0){
        props.commentData.showReply=!show;
    }
    else{
        props.commentData.showReply=true;
    }
    replyUserId.value= curData.userId;
    placeholderInfo.value = curData.userId;
}

const postFinishHandle = (resultData)=>{
    props.commentData.children = resultData.children;
    props.commentData.showReply=false;
}

//删除
const deleteHandle = (id) => {
    deleteCommentApi(id).then(res=>{
        const data= res.data;
        if(data.code==200){
            proxy.Message.success("删除成功!");
           emit("deleteLoadHandle");
        }
        else{
            proxy.Message.error(data.msg);
        }

    })
}

const setTopHandle=(cdata)=>{
    console.log(1);
    if(cdata.isTop==0){
        cdata.isTop=1;
        setTopCommentApi(cdata).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("置顶成功!");
                emit("deleteLoadHandle")
            }
            else{
                proxy.Message.error(data.msg);
                cdata.isTop=0;
            }
        })
    }
    else if(cdata.isTop == 1){
        cdata.isTop= 0;
        unTopCommentApi(cdata).then(res=>{
            const data = res.data;
            if(data.code==200){
                proxy.Message.success("取消置顶成功!");
                emit("deleteLoadHandle")
            }
            else{
                proxy.Message.error(data.msg);
                cdata.isTop=1;
            }
        })
    }

}


//点赞
const likeClickHandle=(cData)=>{
    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    }
    let likeData = ref({});
    // if(type==1){
        likeData.value={commentId:cData.id}
    // }
    // else if(type==0){
    //     data.value={commentId:id}
    // }
    if(!cData.flag){
        saveLikeApi(likeData.value).then(res=>{
            const data = res.data;
            if(data.code==200)
            {
                cData.flag=true;
                cData.likes++;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else{
        deleteLikeApi(likeData.value).then(res=>{
            const data = res.data;
            if(data.code==200)
            {
                cData.flag=false;
                cData.likes--;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
}

</script>

<style lang="scss" scoped>
    .comment-item{
        display: flex;
        padding-top: 15px;
        .comment-info{
            flex: 1;
            margin-left: 10px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 15px;
            .user-name{
                .name{
                    font-size: 15px;
                    color: #61666d;
                    margin-right: 10px;
                    cursor: pointer;
                }
                .tag-author{
                    background: pink;
                    color: #fff;
                    font-size: 12px;
                    border-radius: 2px;
                }
            }
            .comment-content{
                .top{
                    padding: 0 5px;
                    border-radius: 5px;
                    border: 1px solid rgb(246, 84, 89);
                    font-size: 13px;
                    margin-right: 5px;
                    color: rgb(246, 84, 89);
                }
                margin-top: 5px;
                font-size: 15px;
                line-height: 22px;
            }
            .op-panel{
                display: flex;
                align-items: center;
                margin-top: 5px;
                font-size: 13px;
                color: gray;
                .time{
                    margin-right: 20px;
                }
                .iconfont{
                    cursor: pointer;
                    font-size: 14px;
                    margin-right: 15px;
                    outline: none
                }
                .iconfont::before{
                    margin-right: 3px;
                }
            }
            .comment-sub-list{
                .comment-sub-item{
                    display: flex;
                    padding-top: 15px;
                    .comment-info{
                        flex: 1;
                        margin-left: 10px;
                        padding-bottom: 15px;
                        border: none;
                        .user-name{
                            .name{
                                font-size: 15px;
                                color: #61666d;
                                margin-right: 10px;
                                cursor: pointer;
                            }
                            .tag-author{
                                background: pink;
                                color: #fff;
                                font-size: 12px;
                                border-radius: 2px;
                                margin-right: 10px;
                            }
                            .reply-user{
                                cursor: pointer;
                                color:#2366ff;
                            }
                            .reply-content{
                                margin-left: 1px;
                            }
                        }
                        .op-panel{
                            display: flex;
                            align-items: center;
                            margin-top: 5px;
                            font-size: 13px;
                            color: gray;
                            .time{
                                margin-right: 20px;
                            }
                            .iconfont{
                                cursor: pointer;
                                font-size: 14px;
                                margin-right: 15px;
                                outline: none
                            }
                            .iconfont::before{
                                margin-right: 3px;
                            }
                        }
                    }
                }
            }
            .reply-info{
                margin-top: 10px;
            }
        }
    }
</style>
