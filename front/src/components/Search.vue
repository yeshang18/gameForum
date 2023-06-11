<template>
  <div class="search">
    <el-form
      :model="formData"
      :rules="rules"
      ref="formDataRef"
      @submit.prevent
    >
    <div class="search-btn">
        <el-form-item  prop="title" class="search-input">
        <el-input clearable :placeholder="searchInfo" v-model.trim="formData.title"></el-input>
        </el-form-item> 
        <el-form-item  >
        <el-button type = "primary" @click="searchHandle()">搜索</el-button>
        </el-form-item> 
    </div>
      
      </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
import { useRoute,useRouter } from "vue-router";

const { proxy } = getCurrentInstance();

const route = useRoute();
const router = useRouter();
const formData = ref({});
const formDataRef = ref();
const rules = {
};

const searchInfo = ref("攻略");

const searchHandle=()=>{
  if(formData.value.title==undefined||formData.value.title==null){
    formData.value.title=searchInfo;
  }
  router.push("/searchArticle/"+formData.value.title)
}

</script>

<style lang="scss" scoped>

.search{
    flex: 1;
    .search-btn{
        width: 80%;
        display: flex;
        padding-top: 15px;
        .search-input{
            flex: 1;
            margin-right: 2px;
        }
    }
}
</style>
