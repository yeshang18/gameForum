<template>
  <div class="end">
    <el-container class="layout-container-demo" style="height: calc(100vh - 60px)">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="['1', '3']">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><icon-menu /></el-icon>游戏相关管理
            </template>
            <el-menu-item-group>
              <template #title>游戏分类</template>
              <el-menu-item index="1-1" @click="showTable(1)">游戏平台</el-menu-item>
              <el-menu-item index="1-2" @click="showTable(2)">游戏类型</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="游戏详情">
              <el-menu-item index="1-3" @click="showTable(3)">游戏列表</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="1-4">
              <template #title>Option4</template>
              <el-menu-item index="1-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><message /></el-icon>用户反馈
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="2-1">Option 1</el-menu-item>
              <el-menu-item index="2-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="2-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="2-4">
              <template #title>Option 4</template>
              <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><setting /></el-icon>论坛设置
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="3-1">Option 1</el-menu-item>
              <el-menu-item index="3-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="3-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="3-4">
              <template #title>Option 4</template>
              <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: center; font-size: 12px">
        <div class="toolbar">
          <el-button type="primary" @click="insertShow(tableType,0,null)">添加</el-button>
          <span style="display: flex; font-size: 15px;"><RouterLink to="/">&lt返回论坛</RouterLink></span>
        </div>
      </el-header>

      <el-main>
        <el-scrollbar>
          <el-table :data="tableInfo.records" max-height="85vh" stripe>
            <el-table-column prop="id" label="id" width="100" />
            <el-table-column prop="platformName" label="名称" width="120" v-if="tableType==1"/>
            <el-table-column prop="typeName" label="名称" width="120" v-if="tableType==2"/>
            <el-table-column prop="name" label="名称" width="120" v-if="tableType==3"/>
            <el-table-column prop="type" label="类型" width="120" v-if="tableType==3"/>
            <el-table-column prop="platform" label="平台" width="120" v-if="tableType==3"/>
            <el-table-column prop="description" label="描述" width="120" v-if="tableType==3"/>
            <el-table-column prop="createUser" label="创建人" width="120" />
            <el-table-column prop="updateUser" label="最后更新人" width="120" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column prop="updateTime" label="最后更新时间" width="180" />
            <el-table-column fixed="right" label="操作" width="120">
                <template #default = "scope">
                    <el-button link type="primary" size="small" @click="handleClick(tableType,scope.row.id)">删除</el-button>
                    <el-button link type="primary" size="small" @click="insertShow(tableType,1,scope.row)">更新</el-button>
                </template>
            </el-table-column>
          </el-table>
          <div class="pagination" v-if="tableType==3">
            <el-pagination
            v-if="tableInfo.pages>1"
            background
            :page-size="tableInfo.size"
            :current-page.sync="tableInfo.current"
            layout="prev, pager, next"
            @current-change="handlePageNoChange"
            :total="tableInfo.total"
            style="text-align: right;"
          />
        </div>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
  </div>
  <div>
    <Insert ref = "insertRef"></Insert>
  </div>
</template>

<script setup>
import Insert from './Insert.vue';
import { Menu as IconMenu, Message, Setting } from '@element-plus/icons-vue'

import { ref,onMounted,watch,getCurrentInstance,reactive,} from 'vue';
import { useStore } from 'vuex';
import { getUserByIdApi,getGameByTypeApi,getGameApi,getPlatformApi,getGameTypeApi, deletePlatformApi, deleteGameTypeApi, deleteGameApi, } from "@/api";
const { proxy } = getCurrentInstance();

const store =useStore();

const insertRef =ref()
const insertShow = (type,UorI,data) =>{
  if(data!=null){
    let dataNew = JSON.parse(JSON.stringify(data));
    insertRef.value.showTypeSet(type,UorI,dataNew);
  }else{
    insertRef.value.showTypeSet(type,UorI,data);
  }
} 



onMounted(()=>{
    showTable(1);
})
const tableType = ref(1);
const showTable =(type)=>{
    tableType.value=type;
    getTableInfo(type);
}

const tableInfo = ref({});
// const gameInfo=ref({});
// const gameTypeInfo = ref({});
// const platformInfo =ref({});

const getTableInfo=(type)=>{
    if(type==1){
        getPlatformInfo(1);
        console.log(1);
        // tableInfo = platformInfo;
    }
    else if(type==2){
        getGameTypeInfo(1);
        // tableInfo = gameTypeInfo;
    }
    else if(type == 3){
        getGameInfo(1);
        // tableInfo = gameInfo;
    }
}
//获取平台信息
const getPlatformInfo =(pageNo,pageSize) =>{
    getPlatformApi(pageNo,pageSize).then(res=>{
      console.log(2);
        const data = res.data;
        if(data.code==200){
          // store.commit("updatePlatformInfo",data.data)
            tableInfo.value = data.data;

        }
        else{
            tableInfo.value={}
        }
    })
}
//获取游戏类型
const getGameTypeInfo =(pageNo,pageSize) =>{
    getGameTypeApi(pageNo,pageSize).then(res=>{
        const data = res.data;
        if(data.code==200){
          // store.commit("updateGameTypeInfo",data.data)
          tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
    })
}
//获取游戏信息
const getGameInfo =(pageNo,pageSize) =>{
    getGameApi(pageNo,pageSize).then(res=>{
        const data = res.data;
        if(data.code==200){
            // store.commit("updateGameInfo",data.data)
           tableInfo.value = data.data;
        }
        else{
            tableInfo.value={}
        }
    })
}
//分页
const handlePageNoChange = (current)=>{
  if(tableType.value==1){
        getPlatformInfo(current);
        // tableInfo = platformInfo;
    }
    else if(tableType.value==2){
        getGameTypeInfo(current);
        // tableInfo = gameTypeInfo;
    }
    else if(tableType.value == 3){
        getGameInfo(current);
        // tableInfo = gameInfo;
    }

}


watch(() =>store.state.updateFlag,
 (newVal, oldVal) => {
    if(newVal==1){
      getTableInfo(1);
    }
    else if(newVal ==2){
      getTableInfo(2);
    }
    else if(newVal==3){
      getTableInfo(3);
    }
    store.commit("setUpdateFlag",0);
 }, { immediate: true, deep: true });
// watch(
//   () =>store.state.tableInfo, 
//   (newVal, oldVal) => {
//     if(newVal!=undefined&&newVal!=null){
//         tableInfo.value=newVal;
//     }
//     // else{
//     //   tableInfo.value={};
//     // }
//   }, { immediate: true, deep: true }
// );
// watch(() => store.state.gameInfo,
// (newVal, oldVal) => {
//     if(newVal!=undefined&&newVal!=null){
//         gameInfo.value=newVal;
//     }else{
//         gameInfo.value={};
//     }
// }, 
// { immediate: true, deep: true }
// );

// watch(() => store.state.gameTypeInfo,
// (newVal, oldVal) => {
//     if(newVal!=undefined&&newVal!=null){
//         gameTypeInfo.value=newVal;
//     }else{
//         gameTypeInfo.value={};
//     }
// }, 
// { immediate: true, deep: true }
// );

// watch(() => store.state.platformInfo,
// (newVal, oldVal) => {
//     if(newVal!=undefined&&newVal!=null){
//         platformInfo.value=newVal;
//     }
//     else{
//         platformInfo.value={};
//     }
// }, 
// { immediate: true, deep: true }
// );


//删除更新拦截
const handleClick =(type,id)=>{
  if(type == 1){
    deletePlatformApi(id).then(res=>{
      const data = res.data;
      if(data.code==200){
        proxy.Message.success("删除成功");
        store.commit("setUpdateFlag",1);
      }
    })
  }
  else if(type == 2){
    deleteGameTypeApi(id).then(res=>{
      const data = res.data;
      if(data.code==200){
        proxy.Message.success("删除成功");
        store.commit("setUpdateFlag",2);
      }
    })
  }
  if(type == 3){
    deleteGameApi(id).then(res=>{
      const data = res.data;
      if(data.code==200){
        proxy.Message.success("删除成功");
        store.commit("setUpdateFlag",3);
      }
    })
  }
}

</script>

<style lang="scss" scoped>
  .pagination{
    margin-top: 10px;
  }
</style>
