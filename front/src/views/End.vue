<template>
  <div class="end">
    <el-container class="layout-container-demo" style="height: calc(100vh - 60px)">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="['1', '2','3']">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><icon-menu /></el-icon>游戏相关管理
            </template>
            <el-menu-item-group>
              <template #title>游戏分类</template>
              <el-menu-item index="1-1" @click="showTable(1,1)">游戏平台</el-menu-item>
              <el-menu-item index="1-2" @click="showTable(1,2)">游戏类型</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="游戏详情">
              <el-menu-item index="1-3" @click="showTable(1,3)">游戏列表</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><message /></el-icon>用户反馈
            </template>
            <el-menu-item-group>
              <template #title>平台反馈</template>
              <el-menu-item index="2-1" @click="showTable(2,1)">添加平台</el-menu-item>
              <!-- <el-menu-item index="2-2">修改平台</el-menu-item> -->
            </el-menu-item-group>
            <el-menu-item-group>
              <template #title>类型反馈</template>
              <el-menu-item index="2-3" @click="showTable(2,2)">添加类型</el-menu-item>
              <!-- <el-menu-item index="2-4">修改类型</el-menu-item> -->
            </el-menu-item-group>
            <el-menu-item-group>
              <template #title>游戏反馈</template>
              <el-menu-item index="2-5" @click="showTable(2,3)">添加游戏</el-menu-item>
              <!-- <el-menu-item index="2-6">修改游戏</el-menu-item> -->
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><setting /></el-icon>论坛设置
            </template>
            <el-menu-item-group>
              <template #title>文章类型</template>
              <el-menu-item index="3-1" @click="showTable(3,1)">类型详情</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="发帖收益">
              <el-menu-item index="3-2" @click="showTable(3,2)">积分经验</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-main>
        <el-scrollbar>
          <div class="main-info">
            <div class="table-area">
              <el-card :body-style="{padding:'5px'}">
              <template #header>
                <div class="table-title">
                  <span>信息</span>
                </div>
              </template>
            <div class="table-info">
            <Endtable :table-info="tableInfo" 
            :t-class-type="tClassType" 
            :table-type="tableType" 
            @delete-handle="deleteHandle"
            @pageNoHandle="pageNoHandle"
            @updateHandle="updateHandle"></Endtable>
          </div>
        </el-card>
    </div>
    <div class="insert-update">
        <el-card class="box-card">
          <template #header>
            <span>{{ insertFlag==0?'新增':'更新' }}</span> 
            <span class="return-insert" @click="returnInsertHandle()" v-if="insertFlag==1">
              返回新增
            </span>
          </template>
          <div class="form-info">
            <EndInsert :t-class-type="tClassType" 
            :table-type="tableType" 
            :form-data="dataRow" 
            :insert-flag="insertFlag"
            @reload-info="reloadInfo"></EndInsert>
          </div>
        </el-card>
    </div>
  </div>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
  </div>

</template>

<script setup>
import EndInsert from '../components/EndInsert.vue'
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'

import { ref,onMounted,watch,getCurrentInstance,reactive,} from 'vue';
import { useStore } from 'vuex';
import { getUserByIdApi,getGameByTypeApi,getGameApi,getPlatformApi,getGameTypeApi, deletePlatformApi, deleteGameTypeApi, deleteGameApi, deleteArticleTypeApi, deleteForumSettingApi, } from "@/api";
import Endtable from '@/components/Endtable.vue';
import { getArticleTypeApi } from '@/api';
import { getforumSettingApi } from '@/api';
import { useRouter } from 'vue-router';
const { proxy } = getCurrentInstance();

const store =useStore();
const router = useRouter();

//检测用户
onMounted(()=>{
  if(proxy.VueCookies.isKey("loginInfo")){
    let id = proxy.VueCookies.get("loginInfo").id;
    getUserByIdApi(id).then(res=>{
            const data= res.data;
            if(data.code == 200){

                if(data.data.power!==1){
                  router.push("/");
                  proxy.Message.error("非管理员用户");
                }
            }
            else{
              router.push("/");
              proxy.Message.error("非管理员用户");
            }
        })

  }
  else{
      router.push("/");
      store.commit("showLogin",true);
    }
 
})





//formData
const dataRow =ref({});

onMounted(()=>{
    showTable(1,1);
})

const returnInsertHandle=()=>{
  insertFlag.value=0;
  dataRow.value={};
}

const tClassType =ref(1);

const tableType = ref(1);
const showTable = async(cType,tType,pageNo=1,pageSize=10)=>{
    insertFlag.value=0;
    tClassType.value=cType
    tableType.value=tType;
    nowPageNo.value=pageNo;
    nowPageSize.value=pageSize;
    getTableInfo(cType,tType,pageNo,pageSize);
}

const tableInfo = ref({});

const nowPageNo=ref(1);
const nowPageSize=ref(10);

const getTableInfo=(cType,tType,pageNo,pageSize)=>{

  if(cType==1){
    if(tType==1){
        getPlatformInfo(pageNo,pageSize);
    }
    else if(tType==2){
        getGameTypeInfo(pageNo,pageSize);
    }
    else if(tType == 3){
        getGameInfo(pageNo,pageSize);
    }
  }
  else if(cType==2){
    if(tType==1){
        getPlatformInfo(pageNo,pageSize,0);
    }
    else if(tType==2){
        getGameTypeInfo(pageNo,pageSize,0);
    }
    else if(tType == 3){
        getGameInfo(pageNo,pageSize,0);
    }
  }
  else if(cType==3){
    if(tType==1){
       getArticleTypeInfo(pageNo,pageSize);
    }
    else if(tType==2){
        getforumSettingInfo(pageNo,pageSize);
    }
  }
}
//获取平台信息
const getPlatformInfo =(pageNo,pageSize,status) =>{
    getPlatformApi(pageNo,pageSize,status).then(res=>{
        const data = res.data;
        if(data.code==200){
            tableInfo.value = data.data;

        }
        else{
            tableInfo.value={}
        }
    })
}
//获取游戏类型
const getGameTypeInfo =(pageNo,pageSize,status) =>{
    getGameTypeApi(pageNo,pageSize,status).then(res=>{
        const data = res.data;
        if(data.code==200){
          tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
    })
}
//获取游戏信息
const getGameInfo =(pageNo,pageSize,status) =>{
    getGameApi(pageNo,pageSize,status).then(res=>{
        const data = res.data;
        if(data.code==200){
           tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
    })
}
//获取文章设置
const getArticleTypeInfo=(pageNo,pageSize)=>{
  getArticleTypeApi(pageNo,pageSize).then(res=>{
    const data = res.data;
        if(data.code==200){
           tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
  })
}

//获取论坛设置
const getforumSettingInfo=(pageNo,pageSize)=>{
  getforumSettingApi(pageNo,pageSize).then(res=>{
    const data = res.data;
        if(data.code==200){
           tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
  })
}


//分页拦截
const pageNoHandle = (pageNo)=>{
  showTable(tClassType.value,tableType.value,pageNo,nowPageSize.value);
}

//操作后刷新页面
const reloadInfo = async()=>{
  await showTable(tClassType.value,tableType.value,nowPageNo.value,nowPageSize.value);
  console.log("刷新");
  if(tableInfo.value.records.lenght==0&&nowPageNo.value>1){
    showTable(tClassType.value,tableType.value,nowPageNo.value-1,nowPageSize.value)
  }

}


//删除拦截
const deleteHandle = async(tClassType,tableType,id)=>{
  if(tClassType==1||tClassType==2){
    if(tableType == 1){
      await deletePlatformApi(id).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("删除成功");
        }
      })
    }
    else if(tableType == 2){
      await deleteGameTypeApi(id).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("删除成功");
          // store.commit("setUpdateFlag",2);
        }
      })
    }
    if(tableType == 3){
      await deleteGameApi(id).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("删除成功");
          // store.commit("setUpdateFlag",3);
        }
      })
    }
  }
  else if(tClassType==3){
    if(tableType==1){
      await deleteArticleTypeApi({id:id}).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("删除成功");
        }
      })
    }
    else if(tableType==2){
      await deleteForumSettingApi({id:id}).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("删除成功");
        }
      })
    }
  }
  reloadInfo();
}



const insertFlag = ref(0)
//更新拦截
const updateHandle = (data)=>{
  dataRow.value = data;
  insertFlag.value=1;
}



</script>

<style lang="scss" scoped>
  .main-info{
    display: flex;
    .table-area{
      max-width: 1200px;
      flex: 1;
      margin-right: 10px;
    }
    .insert-update{
      min-width: 470px;
      width: 470px;
      .return-insert{
        font-size: 13px;
        margin-left: 300px;
        cursor: pointer;
        color: #2366ff;
      }
    }
  }
 
</style>
