<template>
  <div class="article-item">
    <div class="article-item-inner">
        <div class="article-body">
            <div class="user-info">
                <Avatar :width="30" :userId="data.userId" :url="data.userImg"></Avatar>
                <RouterLink :to="'/user/'+data.userId" class="a-link">{{ data.userName }}</RouterLink>
                <el-divider direction="vertical"></el-divider>
                <div class="post-time">{{ data.createTime }}</div>
                <el-divider direction="vertical"></el-divider>
                <div class="game-name">{{ data.gameName }}</div>
                <el-divider direction="vertical"></el-divider>
                <div class="type-name">{{ data.typeName }}</div>
                <el-dropdown v-if="currentUserInfo.power==1">
                    <div class="iconfont icon-more"></div>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>
                                    <div @click="setTopHandle(data)">
                                        {{ data.isTop==0?"置顶":"取消置顶" }}
                                    </div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="deleteHandle(data)">删除该贴</div>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                 </el-dropdown>
            </div>
            <RouterLink :to="`/article/${data.id}`" class="title">
            <span v-if="data.isTop==1&&showTop == 0" class="_top">置顶</span>
            <span> {{ data.title }}</span>
            </RouterLink>
            <div class="description-info" v-if="data.description">
                {{ data.description }}
            </div>
            <div class="article-info">
                <span class="iconfont icon-eye-solid">
                    {{ data.view == 0?"浏览":data.view }}
                </span>
                <span class="iconfont icon-good" :style="{color:data.flag?'red':''}">
                    {{ data.likes ==0?"点赞":data.likes }}
                </span>
                <span class="iconfont icon-comment">
                    {{ data.comments ==null?"回复":data.comments }}
                </span>
            </div>
        </div>
        <div v-if="data.cover" class="cover">
            <el-image style="width: 100px; height: 100px" :src="data.cover" :fit="'fill'" />
    </div>  
    </div>
    
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick,watch, onMounted} from "vue"
import {useStore} from "vuex";
import { deleteArticleApi, setTopArticleApi,unTopArticleApi} from "@/api";

const { proxy } = getCurrentInstance();

const store =useStore();

const props =defineProps({
    data:{
        type:Object
    },
    showTop:{
        type:Number,
        default: 0
    }
})


const currentUserInfo = ref({});
watch(() =>store.state.loginUserInfo,
 (newVal, oldVal) => {
    currentUserInfo.value=newVal||{};
 }, { immediate: true, deep: true });


//删除帖子
const emit = defineEmits(["deleteHandle"])
const deleteHandle =(data)=>{
    deleteArticleApi(data).then(res=>{
        const data = res.data;
        if(data.code==200){
            proxy.Message.success("删除成功!");
            emit("deleteHandle")
        }
        else{
            proxy.Message.error(data.msg);
        }
    })
}
//置顶
const setTopHandle=(cdata)=>{   
    if(cdata.isTop==0){
        cdata.isTop= cdata.isTop==0?1:0;
        setTopArticleApi({
            id:cdata.id,
            isTop:cdata.isTop
        }).then(res=>{
            const data = res.data;
            if(data.code==200){
                proxy.Message.success("置顶成功!");
            }
            else{
                proxy.Message.error(data.msg);
                cdata.isTop= data.isTop==0?1:0;
            }
        })
    }
    else if(cdata.isTop == 1){
        cdata.isTop= cdata.isTop==0?1:0;
        unTopArticleApi({
            id:cdata.id,
            isTop:cdata.isTop
        }).then(res=>{
            const data = res.data;
            if(data.code==200){
                proxy.Message.success("置顶成功!");
            }
            else{
                proxy.Message.error(data.msg);
               cdata.isTop= data.isTop==0?1:0;
            }
        })
    }
}



</script>

<style lang="scss" scoped>
.article-item{
    padding: 5px 10px 0px 10px;
    .article-item-inner{
        border-bottom: 1px solid #ddd;
        padding: 10px 0px;
        display: flex;
        .article-body{
            flex: 1;
            .user-info{
                display: flex;
                align-items: center;
                font-size: 15px;
                .post-time{
                    color: #5f5d5d;
                }
                .iconfont{
                    margin-left: 5px;
                    border: none;
                    cursor: pointer;
                    outline: none;
                }
                .a-link{
                    margin-left: 5px;
                }
                .a-link:hover{
                    color: #2366ff
                }
            }
            .title{
                font-size: 17px;
                font-weight: bold;
                margin:10px 0px;
                display: inline-block;
                ._top{
                    padding: 0 5px;
                    border-radius: 5px;
                    border: 1px solid rgb(246, 84, 89);
                    font-size: 13px;
                    margin-right: 5px;
                    color: rgb(246, 84, 89);
                }
            }
            .title:hover{
                color: #2366ff
            }
            .description-info{
                color: #86909c;
                margin-bottom: 10px;
            }
            .article-info{
                display: flex;
                align-items: center;
                font-size: 13px;
                .iconfont{
                    color: #86909c;
                    margin-right: 25px;
                    font-size: 14px;
                }
                .iconfont::before{
                    padding-right: 3px;
                }
            }
        }
        .cover{
            margin-right: 10px;
        }
    }
    
    
}
.article-item:hover{
    background-color: #ddd;
}
</style>
