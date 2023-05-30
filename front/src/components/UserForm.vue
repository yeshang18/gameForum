<template>
  <div class="user-form">
    <el-form
      :model="formData"
      :rules="rules"
      ref="formDataRef"
      label-width="80px"
      @submit.prevent
    >
    <!--input输入-->
      <el-form-item label="昵称" prop="name" >
        <el-input clearable placeholder="请输入昵称" v-model.trim="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone" >
        <el-input clearable placeholder="请输入手机号" v-model.trim="formData.phone"></el-input>
      </el-form-item>
    <!--textarea输入-->
      <el-form-item label="个人介绍" prop="description">
        <el-input
          clearable
          placeholder="请输入个人介绍"
          type="textarea"
          :rows="5"
          :maxlength="150"
          resize="none"
          show-word-limit
          v-model.trim="formData.description"
      ></el-input>
      </el-form-item>
    <!-- 单选 -->
      <el-form-item label="性别" prop="sex" >
        <el-radio-group v-model="formData.sex">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="0">女</el-radio>
          <el-radio :label="3">保密</el-radio>
        </el-radio-group>
      </el-form-item>
     <el-form-item label-width="0">
        <el-button type="primary" :style="{margin : '0 auto'}" @click="updateHandle(formData)">保存</el-button>
    </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
import { updateUserApi } from "@/api";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const { proxy } = getCurrentInstance();

const store = useStore();
const router = useRouter();

const props = defineProps({
    formData:{
        type:Object
    }
})

const formDataRef = ref();
const rules = {
  name: [{ required: true, message: "请输入昵称",trigger:"blur" }],
  phone:[{required: true,message:"请输入手机号",trigger:"blur"},
            {validator:proxy.Verify.phone,message:"请输入正确的手机号",trigger:"blur"}],
  sex: [{ required: true, message: "请选择性别" ,trigger:"blur"}],
};


const updateHandle =async (cdata)=> {

    console.log(1);
    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    } 
    console.log(2);
  await formDataRef.value.validate((valid)=>{
    if(!valid){
      return;
    }
    console.log(3);
    console.log(cdata);
   updateUserApi(cdata).then(res=>{
      const data = res.data;
      if(data.code = 200)
      {
        location.reload();
      }
      else{
        proxy.Message.error(data.msg);
      }
    })
  })
}


</script>

<style lang="scss" scoped>
</style>
