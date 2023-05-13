<template>
    <div>
        <Dialog
        :show="dialogConfig.show"
        :title="dialogConfig.title"
        :buttons="dialogConfig.buttons"
        width="400px"
        :showCancel="false"
        @close="dialogConfig.show = false">
            <div class="login-register"> 
            <el-form
              :model="formData"
              :rules="rules"
              ref="formDataRef"
              label-width="80px"
              @submit.prevent
            >
            <!--input输入-->
            <!-- 注册1登陆0 -->
              <el-form-item label="用户名" prop="username" >
                <el-input placeholder="请输入用户名" v-model.trim="formData.username" maxLength="18"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password" >
                <el-input show-password placeholder="请输入密码" v-model.trim="formData.password" type="password"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="upassword" v-show="opType == 1">
                <el-input show-password placeholder="请确认密码" v-model.trim="formData.upassword" type = "password"></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="name" v-show="opType == 1" >
                <el-input placeholder="请输入昵称" v-model.trim="formData.name" maxLength="15"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="phoneNumber" v-show="opType == 1" >
                <el-input placeholder="请输入手机号" v-model.trim="formData.phoneNumber" maxLength="11"></el-input>
              </el-form-item> 
                <div class="option" v-if="opType == 1">
                    <a href="javascript:;" @click="showPanel(0)">已有账号?</a>
                </div>
                <div class="option" v-if="opType == 0">
                    <a href="javascript:void(0)" @click="showPanel(1)">没有账号?</a>
                </div>
                <div class="op-pr" v-if="opType == 1">
                    <el-button type="primary">注册</el-button>
                </div>
                <div class="op-pr" v-if="opType == 0">
                    <el-button type="primary">登陆</el-button>
                </div>      
            </el-form>
        </div>
        </Dialog>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter,useRoute } from "vue-router";
const { proxy } = getCurrentInstance();
const router =useRouter();
const route = useRoute();



const opType =ref(0);



const showPanel = (type)=>{
    opType.value=type;
    resetForm();  
};
defineExpose({showPanel});

const dialogConfig = reactive({
  show: false,
  title: "",
});

const checkUpassword = (rule,value,callback)=>{
  if(value!==formData.value.password){
    callback(new Error(rule.message));
  }
  else{
    callback;
  }
}

const formData = ref({});
const formDataRef = ref();
const rules = {
  username: [{ required: true, message: "请输入用户名"},
            {min: 5,message:"用户名过短,至少为5个字符" },
            {validator:proxy.Verify.username,message:"用户名不符合规范"}],
  password:[{required: true,message:"请输入密码"},
            {validator:proxy.Verify.password,message:"请输入至少6位字母数字或字符"}],
  upassword:[{required: true,message:"请再次输入密码"},
            {validator:checkUpassword,message:"两次输入密码不一致"}],
  phoneNumber:[{required: true,message:"请输入手机号"},
            {validator:proxy.Verify.phone,message:"请输入正确的手机号"}],
  name:[{required: true,message:"请输入昵称"},],

};


const resetForm = ()=>{
  dialogConfig.show=true;
  if(opType == 1){
      dialogConfig.title = "注册";
    }
    else{
      dialogConfig.title = "登陆";
    }
  nextTick(()=>{
    formDataRef.value.resetFields();
  })
}
</script>


<style lang="scss">
    .login-register{      
        display: flex;
        justify-content:center;
        .op-pr{
            display: flex;
            margin:0 auto;
            justify-content:center;
        }
        .option{
                color: blue;
                justify-content: space-between;
            }
    }

    
</style>