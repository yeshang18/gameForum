<template>
  <div class="ucenter" :style="{ width: '1000px'}">
    <div class="user-banner">
        <span>个人中心</span>
    </div>
    <div class="ucenter-panel" >
        <div class="user-side">
            <div class="avatar-panel">
                <div class="edit-btn" v-if="userId == currentUserInfo.id" @click="setShowType(0)">
                    修改资料
                </div>
                <div class="avatar-inner">
                    <Avatar :width="120" :userId="userId"></Avatar>
                </div>
                <div class="user-name">
                    <span>{{ userInfo.name }}</span>
                    <span v-if="userInfo.sex==0" class="iconfont icon-women"></span>
                    <span v-if="userInfo.sex==1" class="iconfont icon-man"></span>
                </div>
                <div class="desc">
                {{ userInfo.description==null?"这个人很懒，什么都没有留~":userInfo.description }}
                </div>
            </div>
            <div class="user-info">
                <div class="level" :style="{color:proxy.Util.level(userInfo.level)?proxy.Util.level(userInfo.level).color:''}">{{proxy.Util.level(userInfo.level)?proxy.Util.level(userInfo.level).info:'' }}</div>
                <div class="integral">积分：{{ userInfo.integral }}</div>
                <div class="likes">获赞量：{{userInfo.totalLikes}}</div>
                <div class="contents">发帖量：{{userInfo.countArticle  }}</div>
            </div>
        </div>
        <div class="article-panel" v-if="showType==1">
            <div class="article-opt">
                <span class="opt-font">
                {{ userId == currentUserInfo.id?"我的帖子":"他的帖子" }}
                </span>
            </div>
            <div class="article-info">
                <DataList :dataSource="articles" @loadData="loadArticle">
                <template #default="{data}">
                    <ArticleListItem :data="data" :showTop="1"></ArticleListItem>
                </template>
            </DataList>
            </div>        
        </div>
        <div class="update-form" v-if="showType==0">
            <!-- <el-icon><ArrowLeft /></el-icon> -->
            <div class="return" @click="setShowType(1)">&lt返回</div>
            <div class="form-info">
                <UserForm :formData = "userInfo"></UserForm>
            </div>
            
        </div>
    </div>
  </div>
</template>

<script setup>
import UserForm from '../components/UserForm.vue'
import ArticleListItem from './ArticleListItem.vue';
import { ref,  getCurrentInstance, onMounted,watch} from "vue"
import { useStore } from 'vuex';
import { useRoute,useRouter } from "vue-router";
import { getUserByIdApi,getArticleByUserApi } from "@/api";

const { proxy } = getCurrentInstance();
const store = useStore();
const route =useRoute()
const router = useRouter();

// const level = proxy.Util.level(userInfo.value.level).info

//用户中心id
const showType=ref(1);

const setShowType = (type)=>{
    showType.value = type;
}


const userId = ref()
const userInfo=ref({});
const loadUserInfo = async ()=>{
   await getUserByIdApi(userId.value).then(res=>{
        const data= res.data;
        if(data.code == 200){
            userInfo.value = data.data;     
        }
        else if(data.code == 404){
            proxy.Message.error(data.msg);
        }
        else {
            proxy.Message.error(data.msg);
        }
    })
}
//当前用户id
const currentUserInfo = ref({});
watch(() =>store.state.loginUserInfo,
 (newVal, oldVal) => {
    currentUserInfo.value=newVal||{};
 }, { immediate: true, deep: true });


const articles= ref([])
//根据用户获取文章
const getArticleByUser = () =>{
    getArticleByUserApi(userId.value).then(res=>{
        const data = res.data;
        if(data.code==200){
            articles.value=data.data;
        }
    })
}
const loadArticle = (pageNo,pageSize)=>{
    getArticleByUserApi(userId.value,pageNo,pageSize).then(res=>{
        const data = res.data;
        if(data.code==200){
            articles.value=data.data;
        }
    })
}


onMounted(()=>{
    userId.value = route.params.userId;
    loadUserInfo();
    getArticleByUser();
})


</script>

<style lang="scss" scoped>
.ucenter{
    margin: 0 auto;
    margin-top: 70px;
    .user-banner{
        color: black;
    }
    .ucenter-panel{
        display: flex;
        .user-side{
            margin-top: 20px;
            width: 300px;
            margin-right: 10px;
            .avatar-panel{
                border-radius: 10px;
                .edit-btn{
                    cursor: pointer;
                    padding-right: 5px;
                    text-align:right;
                    font-size: 14px;
                    color: #2366ff;
                }
                background: #ddd;
                text-align: center;
                padding: 10px 0px;
                .avatar-inner{
                    display: flex;
                    justify-content: center;
                }
                .user-name{
                        .iconfont{
                            margin-left: 5px;
                            color: #2366ff;
                        }
                }
                .desc{
                    padding-left: 10px;
                    text-align: left;
                    font-size: 14px;
                    color: #373b40;
                }
            }
            .user-info{
                border-radius: 10px;
                background: #ddd;
                margin-top: 10px;
                padding: 15px 10px;
                .level{                  
                    font-weight: 600;
                }
                .integral{
                    margin-top: 10px;
                }
                .likes{
                    margin-top: 10px;
                }
                .contents{
                    margin-top: 10px;
                }
            }
        }
        .article-panel{
            margin-left: 20px;
            margin-top: 20px;
            flex: 1;
            
            .article-opt{
                border-radius: 5px;
                background: #fff;              
                flex: 1;
                .opt-font{
                    padding: 4px 4px;
                    font-size: 19px;
                }
            }
            .article-info{
                border-radius: 5px;
                margin-top: 10px;
                background: #fff;
            }
        }
        
        .update-form{
            .return{
                margin-left: 20px;
                cursor: pointer;
                margin-bottom: 10px;
             }
             .return:hover{
                color: #2366ff;
             }
            margin-left: 20px;
            margin-top: 20px;
            flex: 1;
            background: #fff; 
            padding-top: 20px;
            border-radius: 5px;
        }
    }
}
</style>
