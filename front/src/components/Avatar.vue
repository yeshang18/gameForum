<template>
  <div class="avatar" 
  :style="{
    width:width+'px',
    height:width+'px',
    'border-radius':width/2+'px',
  }"
  > 
  <el-image 
    :style="{
        width:width+'px',
        height:width+'px',
        'border-radius':width/2+'px',
    }"
    :src='url'
    fit="fill"
    loading="lazy"
    v-if="userId!=null"
  >
  </el-image>
  <div v-else class="no-login">
    未登录
  </div>
  </div>
</template>

<script setup>
    import { ref, reactive, getCurrentInstance, nextTick } from "vue"
    import { useRouter } from "vue-router";
    const { proxy } = getCurrentInstance();

    const router = useRouter();

    const props =defineProps({
        url:{
            type:String,
            default:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
        },
        userId:{
            type:Number
        },
        width:{
            type:Number,
            default:50
        },
        addLink:{
            type:Boolean,
            default:true
        }
    });


    // const goToUcenter =()=>{
    //     if(props.addLink){
    //         router.push("/user/"+proxy.userId)
    //     }
    // }

</script>

<style lang="scss" scoped>
    .avatar{
        cursor: pointer;
        display: flex;
        background-color: #f0f0f0;
        align-items: center;
        overflow: hidden;
        .no-login{
        width: 100%;
        text-align: center;
        font-size: 13px;
    }
    }
    
</style>
