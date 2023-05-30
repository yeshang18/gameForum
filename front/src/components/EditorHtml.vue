<template>
  <div class="editor-html">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
      />
      <Editor
        :style="{height: height+'px', 'overflow-y': 'hidden'}"
        :model-value="modelValue"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
        @onChange="onChange"
      />
  </div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, getCurrentInstance,ref, shallowRef, onMounted,computed } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const { proxy } = getCurrentInstance();

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

const mode = ref("default")

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()


const toolbarConfig = {
  excludeKeys:["uploadVideo"]
}
const editorConfig = { 
  placeholder: '请输入内容...' ,
  excludeKeys: ["uploadVideo"],
  MENU_CONF:{
    uploadImage:{
      maxFileSize:3*1024*1024,
      server:"http://localhost:8080/file/upload",
      fieldName:"file",
      customInsert(responseData,insertFn){
        if(responseData.code == 200){
          insertFn(
            proxy.$ImgUrl+responseData.data,"",""
          );
          return;
        }else if(responseData.code == 901){
          store.commit("showLogin",true);
          store.commit("updateLoginUserInfo",null);
          return Promise.reject(alert("请登陆!"))
        }
        proxy.Message.error(responseData.msg)
      }
    }
  }
}

const emit = defineEmits(["update:modelValue"]);
const onChange = (editor) =>{
  emit("update:modelValue",editor.getHtml());
}
// const modelValue = computed({
//   get: () => props.modelValue,
//   set: (value) => emit('update:modelValue', value),
// })

onBeforeUnmount(()=>{
  const editor = editorRef.value;
  if(editor==null) return;
  editor.destroy();
})

const handleCreated =(editor)=>{
  editorRef.value =editor;
}

</script>

<style lang="scss" scoped>
.editor-html{
  border: 1px solid #ddd;
}
</style>
