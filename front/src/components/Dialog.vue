<template>
    <el-dialog 
        :model-value="show"
        :show-close = "showClose" 
        :draggable ="true"
        :close-on-click-modal="false"
        :title="title" 
        width="30%" 
        class="custom-dialog"
        top="50px"
    >
        <div class="dialog-body">
            <slot></slot>
        </div>
    </el-dialog>
</template>



<script setup>
import { ref } from 'vue'

// const centerDialogVisible = ref(true)
const props = defineProps({
    show:{
        type:Boolean,
        default:true
    },
    title:{
        type:String,
        default:"注册"
    },
    showClose:{
        type:Boolean,
        default:true,
    },
})
</script>



<style lang="scss">
.custom-dialog{

    .dialog-body{
        min-height: 100px;
        max-height: calc(100vh - 180px);
    }

}
</style>