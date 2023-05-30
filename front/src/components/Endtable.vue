<template>
  <div class="end-tabel">
    <el-table :data="tableInfo.records" max-height="85vh" stripe v-if="tClassType==1||tClassType==2">
            <el-table-column type="index"></el-table-column>
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
                    <el-button link type="primary" size="small" @click="handleClick(scope.row.id)">删除</el-button>
                    <el-button link type="primary" size="small" @click="updateClick(scope.row)">更新</el-button>
                </template>
            </el-table-column>
          </el-table>
          <el-table :data="tableInfo.records" max-height="85vh" stripe v-if="tClassType==3">
        <el-table-column prop="id" label="id" width="50" />        
            <el-table-column prop="typeName" label="名称" width="100" v-if="tableType==1"/>
            <el-table-column prop="integral" label="获得积分" width="100" v-if="tableType==2"/>
            <el-table-column prop="articleExp" label="发帖经验" width="100" v-if="tableType==2"/>
            <el-table-column prop="commentExp" label="评论经验" width="100" v-if="tableType==2"/>
            <el-table-column prop="dayExp" label="每日经验上限" width="120" v-if="tableType==2"/>
            <el-table-column prop="createUser" :label="tClassType==2?'提交用户':'创建人'" width="120" />
            <el-table-column prop="updateUser" label="最后更新人" width="120" />
            <el-table-column prop="createTime" :label="tClassType==2?'提交时间':'创建时间'" width="180" />
            <el-table-column prop="updateTime" label="最后更新时间" width="180" />
        
            <el-table-column fixed="right" label="操作" width="120">
                <template #default = "scope">
                    <el-button link type="primary" size="small" @click="handleClick(scope.row.id)">删除</el-button>
                    <el-button link type="primary" size="small" @click="updateClick(scope.row)">更新</el-button>
                </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
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
  </div>
</template>

<script setup>

import { ref,onMounted,watch,getCurrentInstance,reactive,} from 'vue';
import { useStore } from 'vuex';
const { proxy } = getCurrentInstance();

const store =useStore();

const props = defineProps({
    tableInfo:{
        type:Object
    },
    tClassType:{
        type:Number
    },
    tableType:{
        type:Number
    }
})



//分页
const handlePageNoChange = (current)=>{
  emit("pageNoHandle",current);

}

const emit = defineEmits(["deleteHandle","pageNoHandle","updateHandle"])
//删除更新拦截
const handleClick =(id)=>{
  emit("deleteHandle",props.tClassType,props.tableType,id);
}


const updateClick=(data)=>{
    let dataNew = JSON.parse(JSON.stringify(data));
    emit("updateHandle",dataNew)
}
</script>

<style lang="scss" scoped>
</style>
