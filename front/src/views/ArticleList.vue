<template>
  <div class="forum">
            <el-container>
            <el-header class="header">
                <div class="game-category">
                    <div class="fenlei" @click="setFlag(0)">根据游戏类型分类</div>
                    <el-divider direction="vertical"></el-divider>
                    <div class="fenlei" @click="setFlag(1)">根据游戏平台分类</div>
                </div>
                <div>
                    <el-button type="danger" round 
                    v-for="gameCategory in gameCategorys" :key="gameCategory.id"
                     @click="getGameByType(gameCategory.id)" v-if="gameFlag==0">
                     {{ gameCategory.typeName }}
                    </el-button>
                </div>
                <div>
                    <el-button type="danger" round 
                    v-for="gameCategory in gameCategorys" :key="gameCategory.id"
                     @click="getGameByPlatform(gameCategory.id)" v-if="gameFlag==1">
                     {{ gameCategory.platformName }}
                    </el-button>
                </div>
                
            </el-header>
            <el-container>
                <el-aside width="160px" class="aside">
                    <ul>
                        <li class="game-detail" v-for="game in games" :key="game.id"
                        @click="clickGame(game.id)">
                        {{ game.name }}</li>
                        <!-- <li class="game-detail"><el-button type="success" v-for="game in games" :key="game.id">{{ game.name }}</el-button></li> -->
                    </ul>
                </el-aside>
                <el-container>
                <el-main class="main">
                    <DataList :dataSource="articles" @loadData="loadArticle">
                        <template #default="{data}">
                            <ArticleListItem :data="data"></ArticleListItem>
                        </template>
                    </DataList>
                </el-main>
                <!-- <el-footer class="footer">Footer</el-footer> -->
                </el-container>
            </el-container>
            </el-container>
        </div>
</template>

<script setup>
import ArticleListItem from './ArticleListItem.vue';
import { ref,onMounted,watch,getCurrentInstance } from 'vue';
import { useStore } from 'vuex';
import { getUserByIdApi,getGameByTypeApi,getGameApi,getPlatformApi,
    getGameTypeApi, getGameByPlatformApi,getArticleByGameApi } from "@/api";

onMounted(()=>{
    setFlag(0);
})
const gameFlag=ref(0)
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
            if(gameCategorys.value!=null){
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
            if(gameCategorys.value!=null){
                getGameByPlatform(gameCategorys.value[0].id);
            }
        }
    })
}

const games = ref([]);
const nowGameId = ref();
//根据类型获取游戏
const getGameByType =(typeId)=>{
    getGameByTypeApi(typeId,1,-1).then(res=>{
        const data = res.data;
        if(data.code == 200){
            games.value = data.data.records;
            if(games.value!=null){
                getArticleByGame(games.value[0].id)
            }
        }
    })
}

//根据游戏平台获取游戏
const getGameByPlatform =(typeId)=>{
    getGameByPlatformApi(typeId,1,-1).then(res=>{
        const data = res.data;
        if(data.code == 200){
            games.value = data.data.records;
            if(games.value!=null){
                getArticleByGame(games.value[0].id)
            }
        }
    })
}

const articles= ref([])
//根据游戏获取文章
const getArticleByGame = (gameId) =>{
    getArticleByGameApi(gameId).then(res=>{
        const data = res.data;
        nowGameId.value = gameId;
        if(data.code==200){
            articles.value=data.data;
        }
    })
}
const loadArticle = (pageNo,pageSize)=>{
    getArticleByGameApi(nowGameId.value,pageNo,pageSize).then(res=>{
        const data = res.data;
        if(data.code==200){
            articles.value=data.data;
        }
    })
}

const clickGame=(gameId) =>{
    getArticleByGame(gameId)
}

</script>

<style lang="scss" scoped>
.forum{
        .header{
            padding: 10px;
            height: 100px;
            .game-category{
                height: 30px;
                display: flex;
                .fenlei{
                    cursor: pointer;
                    
                }
                .fenlei:hover{
                    color: #2366ff;
                }
            }

        }
        .aside{
            height: calc(100vh - 160px);
            .game-detail{
                display: block;
                align-items: center;
                justify-content: center;
                height: 50px;
                margin: 10px;
                cursor: pointer;
            }
            .game-detail:hover{
                color: #2366ff;
            }
        }
    }
    .main{
    }

</style>
