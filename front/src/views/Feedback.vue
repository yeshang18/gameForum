<template>
  <div class="feedback">
    <div class="select-item">请选择你想添加的内容:</div>
    <div class="item-panel">
      <span class="item-info" @click="showTypeSet(3)" :style="{color:showType==3?'#2366ff':''}" >游戏</span>
      <span class="item-info" @click="showTypeSet(2)" :style="{color:showType==2?'#2366ff':''}">游戏类型</span>
      <span class="item-info" @click="showTypeSet(1)" :style="{color:showType==1?'#2366ff':''}">游戏平台</span>
    </div>
    <div class="form-info" >
      <el-form
          :model="formData"
          :rules="rules"
          ref="formDataRef"
          label-width="80px"
          @submit.prevent
        >
        <!--input输入-->
          <el-form-item label="名称" prop="platformName" v-if="showType==1">
            <el-input placeholder="请输入平台名称" v-model.trim="formData.platformName"></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="typeName" v-if="showType==2">
            <el-input placeholder="请输入游戏类型名称" v-model.trim="formData.typeName"></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="name" v-if="showType==3">
            <el-input placeholder="请输入游戏名称" v-model.trim="formData.name"></el-input>
          </el-form-item>
        <!-- textarea输入 -->
          <el-form-item label="介绍" prop="description" v-if="showType==3">
            <el-input
              placeholder="请输入游戏介绍"
              type="textarea"
              :rows="5"
              :maxlength="150"
              resize="none"
              show-word-limit
              v-model.trim="formData.description"
          ></el-input>
          </el-form-item>
        <!-- 下拉框 -->
          <el-form-item label="游戏类型" prop="type" v-if="showType==3">
            <el-select placeholder="请选择游戏类型" v-model="formData.type">
              <el-option v-for="gameType in gameTypeInfo" :key="gameType.id" :value="gameType.id" :label="gameType.typeName" />
            </el-select>
          </el-form-item>
          <el-form-item label="游戏平台" prop="platform" v-if="showType==3">
            <el-select placeholder="请选择游戏平台" v-model="formData.platform">
              <el-option v-for="platform in platformInfo" :key="platform.id" :value="platform.id" :label="platform.platformName" />
            </el-select>
          </el-form-item>
          <el-form-item label-width="0">
        <el-button type="primary" :style="{margin : '0 auto'}" @click="insertClick(formData)">提交</el-button>
    </el-form-item>
        </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from "vue"
import { getPlatformApi,getGameTypeApi, addPlatformApi, addGameTypeApi,
        addGameApi,  } from "@/api";
import { useStore } from "vuex";
import store from "@/store";
import router from "@/router";
const { proxy } = getCurrentInstance();

// const store=useStore()
// const userInfo = store.getters.getLoginUserInfo;

const userId = ref();
onMounted(()=>{
  if(proxy.VueCookies.isKey("loginInfo")){
    showTypeSet(3);
  }
  else{
      router.push("/");
      store.commit("showLogin",true);
    }
 
})


const formData = ref({
});
const formDataRef = ref();
const rules = {
};

const showType=ref(3);
const showTypeSet = (type) =>{
  if(type==3){
    getPlatformInfo();
    getGameTypeInfo();

  }
    showType.value=type;
}

const platformInfo=ref({});
const gameTypeInfo=ref({});

//获取平台信息
const getPlatformInfo =() =>{
    getPlatformApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
            platformInfo.value = data.data.records;
        }
        else{
            platformInfo.value={}
        }
    })
}
//获取游戏类型
const getGameTypeInfo =() =>{
    getGameTypeApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
            gameTypeInfo.value = data.data.records;
        }
        else{
            gameTypeInfo.value={}
        }
    })
}

const insertClick=()=>{
  formData.value.status = 0;
    if(showType.value == 1){
        addPlatformApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("提交成功！");
                router.push("/");
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else if(showType.value==2){
        addGameTypeApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("提交成功！");
                router.push("/");
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else if(showType.value==3){
        addGameApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("提交成功！");
                router.push("/");
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
}



</script>

<style lang="scss" scoped>
.feedback{
  width: 1300px;
  background: #fff;
  margin: 0 auto;
  margin-top: 70px;
  padding: 20px 10px;
  .item-panel{
    margin-top: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ddd;
    .item-info{
      padding-left: 20px;
      cursor: pointer;
    }
  }
  .form-info{
    margin-top: 30px;
    width: 700px;
  }
}
</style>
