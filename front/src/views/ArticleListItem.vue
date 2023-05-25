<template>
  <div class="article-item">
    <div class="article-item-inner">
        <div class="article-body">
            <div class="user-info">
                <Avatar :width="30"></Avatar>
                <RouterLink :to="'/user/'+data.user" class="a-link">{{ data.userName }}</RouterLink>
                <el-divider direction="vertical"></el-divider>
                <div class="post-time">{{ data.createTime }}</div>
                <el-divider direction="vertical"></el-divider>
                <div class="game-name">{{ data.gameName }}</div>
                <el-divider direction="vertical"></el-divider>
                <div class="type-name">{{ data.typeName }}</div>
            </div>
            <RouterLink to="/" class="title">{{ data.title }}</RouterLink>
            <div class="article-info">
                <span class="iconfont icon-eye-solid">
                    {{ data.view == 0?"浏览":data.view }}
                </span>
                <span class="iconfont icon-good">
                    {{ data.likes ==0?"点赞":data.likes }}
                </span>
                <span class="iconfont icon-comment">
                    {{ data.comments ==null?"回复":data.comments }}
                </span>
            </div>
        </div>
    </div>
    
  </div>
</template>

<script setup>
const props =defineProps({
    data:{
        type:Object
    }
})
</script>

<style lang="scss" scoped>
.article-item{
    padding: 5px 10px 0px 10px;
    .article-item-inner{
        border-bottom: 1px solid #ddd;
        padding: 10px;
        .article-body{
            .user-info{
                display: flex;
                align-items: center;
                font-size: 15px;
                .post-time{
                    color: #5f5d5d;
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
            }
            .title:hover{
                color: #2366ff
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
    }
}
.article-item:hover{
    background-color: #ddd;
}
</style>
