<template>
  <div class="form-insert">
    <el-form
          :model="formData"
          :rules="rules"
          ref="formDataRef"
          label-width="80px"
          @submit.prevent
          v-if="tClassType==1||tClassType==2"
        >
        <!--input输入-->
          <el-form-item label="名称" prop="platformName" v-if="tableType==1">
            <el-input placeholder="请输入平台名称" v-model.trim="formData.platformName"></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="typeName" v-if="tableType==2">
            <el-input placeholder="请输入游戏类型名称" v-model.trim="formData.typeName"></el-input>
          </el-form-item>
          <el-form-item label="名称" prop="name" v-if="tableType==3">
            <el-input placeholder="请输入游戏名称" v-model.trim="formData.name"></el-input>
          </el-form-item>
        <!-- textarea输入 -->
          <el-form-item label="介绍" prop="description" v-if="tableType==3">
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
          <el-form-item label="游戏类型" prop="type" v-if="tableType==3">
            <el-select placeholder="请选择游戏类型" v-model="formData.type">
              <el-option v-for="gameType in gameTypeInfo" :key="gameType.id" :value="gameType.id" :label="gameType.typeName" />
            </el-select>
          </el-form-item>
          <el-form-item label="游戏平台" prop="platform" v-if="tableType==3">
            <el-select placeholder="请选择游戏平台" v-model="formData.platform">
              <el-option v-for="platform in platformInfo" :key="platform.id" :value="platform.id" :label="platform.platformName" />
            </el-select>
          </el-form-item>
          <el-form-item label-width="0">
        <el-button type="primary" :style="{margin : '0 auto'}" @click="postHandle()" v-if="insertFlag==0&&tClassType!=2">提交</el-button>
        <el-button type="primary" :style="{margin : '0 auto'}" @click="updateHandle()" v-if="insertFlag==1">更新</el-button>
    </el-form-item>
        </el-form>
        <!-- 系统设置 -->
        <el-form
          :model="formData"
          :rules="rules"
          ref="formDataRef"
          label-width="100px"
          @submit.prevent
          v-if="tClassType==3"
        >
        <!--input输入-->
          <el-form-item label="类型名称" prop="typeName" v-if="tableType==1">
            <el-input placeholder="请输入类型名称" v-model.trim="formData.typeName"></el-input>
          </el-form-item>
          <el-form-item label="获得积分" prop="integral" v-if="tableType==2">
            <el-input placeholder="请输入积分值" v-model.trim="formData.integral"></el-input>
          </el-form-item>
          <el-form-item label="发帖经验" prop="articleExp" v-if="tableType==2">
            <el-input placeholder="请输入经验值" v-model.trim="formData.articleExp"></el-input>
          </el-form-item>
          <el-form-item label="评论经验" prop="commentExp" v-if="tableType==2">
            <el-input placeholder="请输入经验值" v-model.trim="formData.commentExp"></el-input>
          </el-form-item>
          <el-form-item label="每日最大经验" prop="dayExp" v-if="tableType==2">
            <el-input placeholder="请输入最大经验值" v-model.trim="formData.dayExp"></el-input>
          </el-form-item>
          <el-form-item label-width="0">
        <el-button type="primary" :style="{margin : '0 auto'}" @click="postHandle()" v-if="insertFlag==0">提交</el-button>
        <el-button type="primary" :style="{margin : '0 auto'}" @click="updateHandle()" v-if="insertFlag==1">更新</el-button>
    </el-form-item>
        </el-form>

  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick ,watch} from "vue"
import { getPlatformApi,getGameTypeApi, addPlatformApi, addGameTypeApi,
        addGameApi,
addArticleTypeApi,
addForumSettingApi,
updatePlatformApi,
updateGameTypeApi,
updateGameApi,
updateArticleTypeApi,
updateForumSettingApi,  } from "@/api";
import { async } from "@kangc/v-md-editor";
const { proxy } = getCurrentInstance();

const props = defineProps({
    formData:{
        type:Object,
        default:{}
    },
    tClassType:{
        type:Number
    },
    tableType:{
        type:Number
    },
    insertFlag:{
        type:Number
    }
})


const formDataRef = ref();
const rules = {
};

watch(()=>props,(newVal, oldVal) => {
    if(newVal.tClassType==1&&newVal.tableType==3){
        getPlatformInfo();
        getGameTypeInfo();
    }
}, { immediate: true, deep: true });

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

const emit = defineEmits(["reloadInfo"])

const postHandle=async ()=>{
    if(props.tClassType==1){
        if(props.tableType == 1){
            await addPlatformApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("提交成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.tableType==2){
            await addGameTypeApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("提交成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.tableType==3){
            await addGameApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("提交成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
    }
    else if(props.tClassType==3){
        if(props.tableType==1){
            await addArticleTypeApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("提交成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.tableType==1){
            await addForumSettingApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("提交成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
    }
    emit("reloadInfo");
}

const updateHandle=async ()=>{
    if(props.tClassType==1||props.tClassType==2){
        if(props.tClassType==2){
            props.formData.status=1;
        }
        if(props.tableType==1){
            await updatePlatformApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("更新成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.tableType==2){
            await updateGameTypeApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("更新成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.tableType==3){
            await updateGameApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("更新成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
    }

    else if(props.tClassType==3){
        if(props.tableType==1){
            await updateArticleTypeApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("更新成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
        else if(props.formData==2){
            await updateForumSettingApi(props.formData).then(res=>{
                const data =res.data;
                if(data.code==200){
                    proxy.Message.success("更新成功！");
                }
                else{
                    proxy.Message.error(data.msg);
                }
            })
        }
    }
    emit("reloadInfo");
}


</script>

<style lang="scss" scoped>


</style>
