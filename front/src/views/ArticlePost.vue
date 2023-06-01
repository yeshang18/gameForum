<template>
  <div>
    <el-form
      :model="formData"
      :rules="rules"
      ref="formDataRef"
      label-width="60px"
      @submit.prevent

      class="post-pannel"
    >
    <div class="post-editor">
        <el-card :body-style="{padding:'5px'}">
          <template #header>
            <div class="post-editor-title">
            <span>正文</span>
            <div class="change-editor-type">
              <span class="iconfont icon-change" @click="changeEditor()">
                切换为{{ editorType==0?'html编辑器':'markdown编辑器' }}
              </span>
            </div>
          </div>
          </template>
          <el-form-item prop="content"  label-width="0">
            <EditorMarkdown :height="markdownHeight" 
            v-model="formData.markdownContent"
            v-if="editorType==0"
            @htmlContent="setHtmlContent"
            >
          </EditorMarkdown>
            <EditorHtml :height="HtmlHeight"  
            v-model="formData.content" 
            v-if="editorType==1">
          </EditorHtml>
          </el-form-item>
          
          <!--  -->
        </el-card>
    </div>
    <div class="post-sttting">
        <el-card class="box-card">
          <template #header>
            <span>设置</span>
            
          </template>
          <div class="form-info">
          <el-form-item label="标题" prop="title" >
            <el-input clearable placeholder="请输入标题" v-model.trim="formData.title" :maxlength="20"></el-input>
        </el-form-item>
        <el-form-item label="游戏" prop="gameId">
            <el-select placeholder="请选择游戏" v-model="formData.gameId">
              <el-option v-for="game in gameInfo" :value="game.id" :label="game.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="类型" prop="articleTypeId">
            <el-select placeholder="请选择帖子类型" v-model="formData.articleTypeId">
              <el-option v-for="articleType in articleTypeInfo" :value="articleType.id" :label="articleType.typeName" />
            </el-select>
          </el-form-item>
          <el-form-item label="封面" prop="cover">
            <CoverUpload v-model="formData.cover"></CoverUpload>
            </el-form-item>
            <el-form-item label="简介" prop="description">
                <el-input
                placeholder="请输入文章简介"
                type="textarea"
                :maxlength="250"
                resize="none"
                show-word-limit
                v-model.trim="formData.description"
                ></el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" :style="{ width:'100%' }" @click="postHandler()">保存</el-button>
            </el-form-item>
          </div>
        </el-card>
    </div>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
const { proxy } = getCurrentInstance();
import {getGameApi,getArticleTypeApi,saveArticleApi,addintegralApi, addLevelApi, getforumSettingApi} from '@/api'
import router from "@/router";
import { useStore } from "vuex";

const store = useStore();

const formData = ref({});
const formDataRef = ref();
const rules = {
  title: [{ required: true, message: "请输入标题" },
],
  content: [{ required: true, message: "请输入内容" }],
  gameId: [{ required: true, message: "请选择游戏" }],
  articleTypeId: [{ required: true, message: "请选择类型" }],
}

const markdownHeight = window.innerHeight-145;

const HtmlHeight = window.innerHeight-60-185;

const editorType = ref(proxy.VueCookies.get("editorType")||0);
const changeEditor =()=>{
    ElMessageBox.confirm("切换编辑器会清空正在编辑的内容，确定要清空吗？","提示").then(()=>{
      editorType.value=editorType.value==0?1:0;
      formData.value.content ="";
      proxy.VueCookies.set("editorType",editorType.value,-1);
    }).catch(() => {
      return;
    })

}

const setHtmlContent = (htmlContent)=>{
  formData.value.content = htmlContent;
}

//提交
const postHandler= async()=>{
  if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    } 
  
  await formDataRef.value.validate((valid)=>{
    if(!valid){
      return;
    }
    saveArticleApi(formData.value).then(res=>{
      const data = res.data;
      if(data.code = 200)
      {
        setIAndLevel();
        router.push("/article/"+data.data.id);
      }
      else{
        proxy.Message.error(data.msg);
      }
    })
  })
}

const setIAndLevel = async()=>{
  await getForumSetting();
  addintegralHandle();
  addLevelHandle();
}
const addintegralHandle=()=>{
  addintegralApi(forumSettingInfo.value.integral).then(res=>{
    if(res.data.code==200){
      return;
    }
    else{
      proxy.Message.error("系统繁忙");
    }
  })
}

const addLevelHandle =()=>{
  const dataF =new FormData();
  dataF.append("num",forumSettingInfo.value.articleExp);
  dataF.append("dayExp",forumSettingInfo.value.dayExp);
  addLevelApi(dataF).then(res=>{
    if(res.data.code==200){
      return
    }
    else{
      proxy.Message.error("系统繁忙");
    }
  })
}


const forumSettingInfo = ref({
      integral:5,
      commentExp:3,
      articleExp:6,
      dayExp:66
});
const getForumSetting = async()=>{
  await getforumSettingApi().then(res=>{
    const data = res.data;
    if(data.code==200){
      if(data.data.length!=0){
        forumSettingInfo.value=data.data.records[0];
      }
      else{
        forumSettingInfo.value={
          integral:5,
          ommentExp:3,
          articleExp:6,
          dayExp:66
        }
      }
    }
    else{
      proxy.Message.error("系统繁忙");
      forumSettingInfo.value={
          integral:5,
          ommentExp:3,
          articleExp:6,
          dayExp:66
        }
    }
  })
}



const gameInfo = ref([])
const articleTypeInfo = ref([])
//获取游戏信息
const getGameInfo =() =>{
    getGameApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
           gameInfo.value = data.data.records;
        }
        else{
            gameInfo.value={}
        }
    })
}
getGameInfo();

const getArticleTypeInfo =() =>{
    getArticleTypeApi(1,-1).then(res=>{
        const data = res.data;
        if(data.code==200){
          articleTypeInfo.value = data.data.records;
        }
        else{
          articleTypeInfo.value={}
        }
    })
}
getArticleTypeInfo();
</script>

<style lang="scss">
.post-pannel{
    display: flex;
    .el-card__header{
        padding: 10px;
    }
    .post-editor{
        min-width: 1000px;
        height: 100%;
        flex: 1;
        .post-editor-title{
            display: flex;
            justify-content: space-between;
            .change-editor-type{            
              .iconfont{
                font-size: 13px;
                cursor: pointer;
                color: #2366ff;
              }
              
            }
        }
    }
    .post-sttting{
        margin-left: 10px;
        width: 450px;
        .box-card{
          .form-info{
            max-height: calc(100vh - 120px );
          }
        }
    }
}
</style>