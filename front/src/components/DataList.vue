<template>
  <div v-for="item in dataSource.records">
    <slot :data="item"></slot>
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

const emit = defineEmits(["loadData"]);
const handlePageNoChange =(current)=>{
    emit("loadData",current,props.dataSource.size);
}
</script>

<style lang="scss" scoped>
</style>
