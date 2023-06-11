<template>
  <div class="article-search">
    <DataList :dataSource="articleInfo" @loadData="loadArticle">
                <template #default="{data}">
                    <ArticleListItem :data="data" :showTop="1"></ArticleListItem>
                </template>
            </DataList>
  </div>
</template>

<script setup>
import ArticleListItem from "./ArticleListItem.vue";
import { getArticleByTitleApi } from "@/api";
import { ref, reactive, getCurrentInstance, nextTick,onMounted,watch } from "vue"
import { useRoute } from "vue-router";
const route = useRoute();
const { proxy } = getCurrentInstance();


const articleTitle = ref();
const articleInfo = ref({})

onMounted(()=>{
    articleTitle.value = route.params.title;

    getArticleByTitle(articleTitle.value);
    
})



const getArticleByTitle = (title,pageNo,pageSize)=>{
    getArticleByTitleApi(title,pageNo,pageSize).then(res=>{
        const data = res.data;
        if(data.code == 200){
            articleInfo.value = data.data;
        }
    })
}
//翻页
const loadArticle = (pageNo,pageSize)=>{
    getArticleByTitle(articleTitle,pageNo,pageSize);
}

watch(() =>route.params.title,
 (newVal, oldVal) => {
    if(newVal){
    articleTitle.value = route.params.title;
    getArticleByTitle(articleTitle.value);
    }
 }, { immediate: true, deep: true });
</script>

<style lang="scss" scoped>
.article-search{
    margin: 0 auto;
    width: 1200px;
    background-color: #fff;
}
</style>
