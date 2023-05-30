<template>
  <div v-for="item in dataSource.records">
    <slot :data="item"></slot>
</div>
<div v-if="dataSource.records&&dataSource.records.length==0">
  <el-empty
    :image-size = "200"
    :image = "img"
    description = "什么都没有，来和刻晴一起摸鱼吧！"
  />
</div>
  <div class="pagination">
    <el-pagination
    v-if="dataSource.pages>1"
    background
    :page-size="dataSource.size"
    :current-page.sync="dataSource.current"
    layout="prev, pager, next"
    @current-change="handlePageNoChange"
    :total="dataSource.total"
    style="text-align: right;"
  />
  </div>
</template>

<script setup>

const props = defineProps({
    dataSource:{
        type:Object
    }
})

const img = require("../assets/img/keqing.jpeg")


const emit = defineEmits(["loadData"]);
const handlePageNoChange =(current)=>{
    emit("loadData",current,props.dataSource.size);
}
</script>

<style lang="scss" scoped>
</style>
