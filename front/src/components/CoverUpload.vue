<!-- <template>
  <div class="cover-upload">
    <el-upload
        name="file"
        class="avatar-uploader"
        accept=".png,.PNG,.jpg,.JPG,.JPEG,.jpeg,.gif,.GIF,.bmp,.BMP"
        :multiple="false"
        :show-file-list="false"
        :http-request="uploadImage()"
    >
        <div class="cover-upload-btn">
            <template v-if="localFile">
            <img :src="localFile" />
            </template>
            <template v-else>
                <img :src="proxy.$ImgUrl+modelValue.imageUrl" 
                v-if="modelValue && modelValue.imageUrl"/>
                <span class="iconfont icon-add" v-else></span>
            </template>
        </div>
    </el-upload>
  </div>
</template>

<script setup>
import { uploadFileApi } from "@/api";
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
const { proxy } = getCurrentInstance();


const props =defineProps({
    modelValue:{
        type:Object,
        default:null,
    },
})

const localFile =ref(null);

const emit = defineEmits(["update:modelValue"])
const uploadImage =async (file)=>{
    // file = file.file;
    // let img = new FileReader();
    // img.readAsDataURL(file);
    // img.onload = ({target})=>{
    //     localFile.value = target.result;
    // };
    let url = "";
    const formdata = new FormData();
    formdata.append('file',file);
    await uploadFileApi(formdata).then(res=>{
    const data = res.data;
    if(data.code==200){
      url = data.data;
    }
  })
  const imgUrl = proxy.$ImgUrl+url;
   emit("update:modelValue",imgUrl)
}

</script>

<style lang="scss" scoped>
.cover-upload{
    .cover-upload-btn{
        background: #efeded;
        width:150px;
        height: 150px;
        display: flex;
        align-items: center;
        justify-content: center;
        .iconfont{
            font-size:50px;
            color:#ddd;
        }
        img{
            width: 100%;
        }
    }

}

</style>
 -->



<template>
    <div>
    <el-upload
        class="avatar-uploader"
        accept=".png,.PNG,.jpg,.JPG,.JPEG,.jpeg,.gif,.GIF,.bmp,.BMP"
        :show-file-list="false"
        :multiple="false"
        :http-request="uploadImage"
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

const imageUrl = ref('')

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