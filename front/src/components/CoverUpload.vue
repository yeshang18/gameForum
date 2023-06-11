<template>
    <div>
    <el-upload
        class="avatar-uploader"
        accept=".png,.PNG,.jpg,.JPG,.JPEG,.jpeg,.gif,.GIF,.bmp,.BMP"
        :show-file-list="false"
        :multiple="false"
        :http-request="uploadImage"
        :before-upload="beforeAvatarUpload"
    >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
</div>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'
import { uploadFileApi } from "@/api";
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
const { proxy } = getCurrentInstance();



const props = defineProps({
    getImageUrl:{
        type:String,
        default:''
    }
})
const imageUrl = ref(props.getImageUrl)


const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片格式错误!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB!')
    return false
  }
  return true
}


const emit = defineEmits(["update:modelValue"])
const uploadImage =async (file)=>{
    let url = "";
    const formdata = new FormData();
    formdata.append('file',file.file);
    await uploadFileApi(formdata).then(res=>{
    const data = res.data;
    if(data.code==200){
        url = data.data;
}
})
imageUrl.value = proxy.$ImgUrl+url;
emit("update:modelValue",imageUrl.value)
}

</script>

<style scoped>
.avatar-uploader .avatar {
width: 178px;
height: 178px;
display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
border: 1px dashed var(--el-border-color);
border-radius: 6px;
cursor: pointer;
position: relative;
overflow: hidden;
transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
font-size: 28px;
color: #8c939d;
width: 178px;
height: 178px;
text-align: center;
}
</style>