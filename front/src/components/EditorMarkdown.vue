<template>
  <v-md-editor 
  :model-value = "modelValue" 
  :height="height+'px'" 
  :disabled-menus="[]"
  :include-level="[1,2,3,4,5,6]"
  @upload-image="uploadImageHandler"
  @change="change"
  >
  
    
  </v-md-editor>
</template>

<script setup>
import { uploadFileApi,downloadFileApi } from "@/api";
import VMdEditor from '@kangc/v-md-editor';
import "@kangc/v-md-editor/lib/style/base-editor.css";
import githubTheme from "@kangc/v-md-editor/lib/theme/github.js"
import "@kangc/v-md-editor/lib/theme/style/github.css";

import hljs from 'highlight.js';

import { ref, reactive, getCurrentInstance, nextTick,computed } from "vue"
const { proxy } = getCurrentInstance();

VMdEditor.use(githubTheme,{
  Hljs:hljs
})

const props = defineProps({
  modelValue:{
    type:String,
    default:""
  },
  height:{
    type:Number,
    default:500
  }

})

const emit = defineEmits(["update:modelValue","htmlContent"]);
const change = (markdownContent,htmlContent) =>{
  emit("update:modelValue",markdownContent);
  emit("htmlContent",htmlContent);
}
// const modelValue = computed({
//   get: () => props.modelValue,
//   set: (value) => emit('update:modelValue', value),
// })


const uploadImageHandler = async (event,insertImage,files)=>{
  let url = "";
  const formdata = new FormData();
  formdata.append('file',files[0]);
  await uploadFileApi(formdata).then(res=>{
    const data = res.data;
    if(data.code==200){
      url = data.data;
    }
  })
  const imgUrl = proxy.$ImgUrl+url;
  insertImage({
    url:imgUrl,
    desc:"图片"
  })
}
</script>

<style lang="scss" scoped>
img{
  width: 60%;
}
</style>
