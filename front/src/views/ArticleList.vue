<template>
  <div class="forum">
        <div class="forum-body">
            <div class="header">
                <div class="game-category">
                    <div class="fenlei" @click="setFlag(0)" :style="{color: gameFlag==0?'#2366ff':''}">根据游戏类型分类</div>
                    <el-divider direction="vertical"></el-divider>
                    <div class="fenlei" @click="setFlag(1)" :style="{color: gameFlag==1?'#2366ff':''}">根据游戏平台分类</div>
                </div>
                <div class="category-info" v-if="gameFlag==0">
                    <div  class="category-item" :style="{background:nowCategoryId==gameCategory.id?'rgb(181, 59, 59)':''}"
                    v-for="gameCategory in gameCategorys"
                     @click="getGameByType(gameCategory.id)" >
                     {{ gameCategory.typeName }}
                    </div>
                </div>
                <div class="category-info" v-if="gameFlag==1">
                    <div class="category-item"
                    v-for="gameCategory in gameCategorys" :style="{background:nowCategoryId==gameCategory.id?'rgb(181, 59, 59)':''}"
                     @click="getGameByPlatform(gameCategory.id)" >
                     {{ gameCategory.platformName }}
                </div>
                </div>               
            </div>
            <div class="game-article">
                <div width="160px" class="game-aside">
                    <div class="game-info" v-for="game in games" :style="{background:nowGameId==game.id?'aqua':''}"
                    @click="clickGame(game.id)">
                    {{ game.name }}</div>
                </div>
                <div class="article-info">
                    <div class="article-type">
                        <span class="type-info" :style="{color:nowTypeId==0?'#2366ff':''}" @click="getArticleByType(0)">全部</span>
                        <div class="type-panel" v-for="articleType in articleTypeInfo">
                            <el-divider direction="vertical"></el-divider>
                            <span class="type-info" :style="{color:nowTypeId==articleType.id?'#2366ff':''}" @click="getArticleByType(articleType.id)" 
                        >{{ articleType.typeName }}</span>
                        
                        </div>
                        
                        <!-- <span class="type-info" :style="{color:nowTypeId==0?'#2366ff':''}">分享</span> -->
                    </div>
                    <DataList :dataSource="articles" @loadData="loadArticle">
                        <template #default="{data}">
                            <ArticleListItem :data="data" @deleteHandle="deleteHandle"></ArticleListItem>
                        </template>
                    </DataList>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import ArticleListItem from './ArticleListItem.vue';
import { ref,onMounted,watch,getCurrentInstance } from 'vue';
import { useStore } from 'vuex';
import { getUserByIdApi,getGameByTypeApi,getGameApi,getPlatformApi,
    getGameTypeApi, getGameByPlatformApi,getArticleByGameApi, getArticleTypeApi } from "@/api";

onMounted(()=>{
    setFlag(0);
    getArticleType();
})
const gameFlag=ref(0)
const nowCategoryId =ref(0);
const gameCategorys = ref([])

const setFlag=(flag)=>{
    gameFlag.value=flag;
    if(flag==0){
        getGameType();
    }
    else if(flag==1){
        getPlatform();
    }
}

//获取游戏类型
const getGameType = () => {
    getGameTypeApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
            gameCategorys.value = data.data.records;
            if(gameCategorys.value.length!=0){
                getGameByType(gameCategorys.value[0].id);
            }
        }
    })
}
//获取游戏平台
const getPlatform = () => {
    getPlatformApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
            gameCategorys.value = data.data.records;
            if(gameCategorys.value.length!=0){
                getGameByPlatform(gameCategorys.value[0].id);
            }
        }
    })
}
//获取文章类型
const articleTypeInfo = ref({});
const getArticleType =()=>{
    getArticleTypeApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
            articleTypeInfo.value = data.data.records;
        }
    })
}


const games = ref([]);
const nowGameId = ref();
//根据类型获取游戏
const getGameByType =(typeId)=>{
    getGameByTypeApi(typeId,1,-1).then(res=>{
        nowCategoryId.value=typeId;
        const data = res.data;
        if(data.code == 200){
            games.value = data.data.records;
            if(games.value.length!=0){
                getArticleByGame(games.value[0].id)
            }
            else{
                nowGameId.value = null;
                articles.value={
                    records:[]
                };
            }
        }
    })
}

//根据游戏平台获取游戏
const getGameByPlatform =(typeId)=>{
    getGameByPlatformApi(typeId,1,-1).then(res=>{
        nowCategoryId.value=typeId;
        const data = res.data;
        if(data.code == 200){
            games.value = data.data.records;
            if(games.value!=null){
                getArticleByGame(games.value[0].id)
            }
        }
    })
}
const deleteHandle =()=>{
    loadArticle(nowPageNo,nowPageSize)
}

const nowPageNo =ref();
const nowPageSize =ref();
//0为全部
const nowTypeId = ref(0)

const articles= ref([])
//根据游戏获取文章
const getArticleByGame = (gameId) =>{
    getArticleByGameApi(gameId).then(res=>{
        const data = res.data;
        nowGameId.value = gameId;
        if(data.code==200){
            articles.value=data.data;
            nowPageNo.value==1;
            nowPageSize.value==10;
        }
    })
}
//根据类型
const getArticleByType = (typeId)=>{
    getArticleByGameApi(nowGameId.value,1,10,typeId).then(res=>{
        nowTypeId.value=typeId;
        const data = res.data;
        if(data.code==200){
            articles.value=data.data;
            nowPageNo.value==1;
            nowPageSize.value==10;
        }
    })
}


const loadArticle = (pageNo,pageSize)=>{
    getArticleByGameApi(nowGameId.value,pageNo,pageSize,nowTypeId.value).then(res=>{
        const data = res.data;
        if(data.code==200){
            articles.value=data.data;
            nowPageNo.value==pageNo;
            nowPageSize.value==pageSize;
        }
    })
}

const clickGame=(gameId) =>{
    nowTypeId.value=0;
    getArticleByGame(gameId);
}

</script>

<style lang="scss" scoped>
.forum{
    width: 1300px;
    margin: 0 auto;
    margin-top: 70px;
    .forum-body{          
        .header{
            margin-top: 30px;
            padding: 10px;
            border-radius: 5px;
            .game-category{
                height: 30px;
                
                border-bottom: 1px solid #ddd;
                display: flex;
                .fenlei{                   
                    cursor: pointer;
                    
                }
                .fenlei:hover{
                    color: #2366ff;
                }
            }
            .category-info{
                margin-top: 10px;
                display: flex;
                flex-wrap: wrap;
                .category-item{
                    background-color: #ddd;
                    margin-right: 5px;
                    text-align: center;
                    font-size: 18px;
                    font-weight: 700;                   
                    width: 125px;
                    padding: 5px 10px;
                    border-radius: 5px;
                    cursor: pointer;
                }
            }

        }
        .game-article{        
            margin-top: 10px;
            display: flex;
            .game-aside{
                height: auto;
                border-radius: 5px;
                width: 150px;
                max-height: calc(100vh - 160px);
                .game-info{
                    background: #fff;
                    margin-top: 3px;
                    padding: 10px;
                    display: block;
                    text-align: center;
                    cursor: pointer;
                    border-bottom: 1px solid #ddd;
                    border-radius: 5px;

                 }
            }
            .article-info{
                opacity: 0.95;
                border-radius: 5px;
                margin-left: 10px;
                background: #fff;
                flex:1;
                .article-type{
                    display: flex;
                    margin-top: 10px;
                    margin-left: 8px;
                    .type-info{
                        padding: 0px 2px;
                        cursor: pointer;
                    }
                    .type-info:hover{
                        color: #2366ff;
                    }
                }
            }
        }
    }
}             
</style>
