<template>
  <div>
    <Dialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      width="500px"
      :showCancel="false"
      @close="dialogConfig.show = false">

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
            <el-select placeholder="请选择游戏类型" v-model="formData.type" class="_type">
              <el-option v-for="gameType in gameTypeInfo" :key="gameType.id" :value="gameType.id" :label="gameType.typeName" />
            </el-select>
          </el-form-item>
          <el-form-item label="游戏平台" prop="platform" v-if="showType==3">
            <el-select placeholder="请选择游戏平台" v-model="formData.platform" class="_paltform">
              <el-option v-for="platform in platformInfo" :key="platform.id" :value="platform.id" :label="platform.platformName" />
            </el-select>
          </el-form-item>
        </el-form>
        <div style="text-align:center">
             <el-button type ="primary" @click="insertClick(showType)" v-if="uOrI==0">添加</el-button>
             <el-button type ="primary" @click="updateClick(showType)" v-if="uOrI==1">更新</el-button>
         </div>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from "vue"
import { getPlatformApi,getGameTypeApi, addPlatformApi, addGameTypeApi,
        addGameApi, updatePlatformApi, updateGameTypeApi,updateGameApi } from "@/api";
import { useStore } from "vuex";
import store from "@/store";
const { proxy } = getCurrentInstance();

// const store=useStore()
// const userInfo = store.getters.getLoginUserInfo;

const dialogConfig = reactive({
    show: false,
    title: "",
});

const formData = ref({
});
const formDataRef = ref();
const rules = {
};

const uOrI =ref(0);
const showType=ref(0);
const showTypeSet = (type,k,data) =>{
    showType.value=type;
    uOrI.value=k;
    resetForm(data); 
}
defineExpose({showTypeSet});
const resetForm = (data)=>{
    dialogConfig.show=true;
    if(showType.value==1){
      if(data){
        dialogConfig.title="更新游戏平台"
      }
      else{
        dialogConfig.title="添加游戏平台"
      }
    }
    else if(showType.value==2){
      if(data){
        dialogConfig.title="更新游戏类型"
      }
      else{
        dialogConfig.title="添加游戏类型"
      }
    }
    else if(showType.value==3){
      if(data){
        dialogConfig.title="更新游戏信息"
      }
      else{
        dialogConfig.title="添加游戏信息"
      }
        getGameTypeInfo();
        getPlatformInfo();
    } 
  nextTick(()=>{
    if(uOrI.value==0){
      formDataRef.value.resetFields();
      formData.value={
          createUser:1,
          updateUser:1,
      }
    }
    else if(uOrI.value==1){
      formDataRef.value.resetFields();
      formData.value=data;
    }
  })
}
// const platform =ref();
// const gameType=ref();
// const game =ref();

const platformInfo=ref();
const gameTypeInfo=ref();
const gameInfo =ref();

//获取平台信息
const getPlatformInfo =() =>{
    getPlatformApi().then(res=>{
        const data = res.data;
        if(data.code==200){
            platformInfo.value = data.data;
        }
        else{
            platformInfo.value={}
        }
    })
}
//获取游戏类型
const getGameTypeInfo =() =>{
    getGameTypeApi().then(res=>{
        const data = res.data;
        if(data.code==200){
            gameTypeInfo.value = data.data;
        }
        else{
            gameTypeInfo.value={}
        }
    })
}
//获取游戏信息
// const getGameInfo =() =>{
//     getGameApi().then(res=>{
//         const data = res.data;
//         if(data.code==200){
//            tableInfo.value = data.data;
//         }
//         else{
//             tableInfo.value={}
//         }
//     })
// }

const insertClick=(type)=>{
    if(type == 1){
        addPlatformApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("添加成功！");
                store.commit("setUpdateFlag",1);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else if(type==2){
        addGameTypeApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("添加成功！");
                store.commit("setUpdateFlag",2);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
    else if(type==3){
        addGameApi(formData.value).then(res=>{
            const data =res.data;
            if(data.code==200){
                proxy.Message.success("添加成功！");
                store.commit("setUpdateFlag",3);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
        })
    }
}

const updateClick=(type)=>{
  if(type==1){
    updatePlatformApi(formData.value).then(res=>{
      const data =res.data;
            if(data.code==200){
                proxy.Message.success("更新成功！");
                store.commit("setUpdateFlag",1);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
    })
  }
  else if(type==2){
    updateGameTypeApi(formData.value).then(res=>{
      const data =res.data;
            if(data.code==200){
                proxy.Message.success("更新成功！");
                store.commit("setUpdateFlag",2);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
    })
  }
  else if(type==3){
    updateGameApi(formData.value).then(res=>{
      const data =res.data;
            if(data.code==200){
                proxy.Message.success("更新成功！");
                store.commit("setUpdateFlag",3);
                dialogConfig.show=false;
            }
            else{
                proxy.Message.error(data.msg);
            }
    })
  }
}
</script>

<style lang="scss" scoped>
</style>
