<template>
    <div>
        <Dialog
        :show="dialogConfig.show"
        :title="dialogConfig.title"
        :buttons="dialogConfig.buttons"
        width="400px"
        :showCancel="false"
        @close="closeDialog()">
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
              <el-form-item label="确认密码" prop="upassword" v-if="opType == 1">
                <el-input show-password placeholder="请确认密码" v-model.trim="formData.upassword" type = "password"></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="name" v-if="opType == 1" >
                <el-input placeholder="请输入昵称" v-model.trim="formData.name" maxLength="15"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="phone" v-if="opType == 1" >
                <el-input placeholder="请输入手机号" v-model.trim="formData.phone" maxLength="11"></el-input>
              </el-form-item> 
                <div class="option" v-if="opType == 1">
                    <a href="javascript:;" @click="showPanel(0)">已有账号?</a>
                </div>
                <div class="option" v-if="opType == 0">
                    <a href="javascript:void(0)" @click="showPanel(1)">没有账号?</a>
                </div>
                <div class="op-pr" v-if="opType == 1">
                    <el-button type="primary" @click="submitForm(formDataRef)">注册</el-button>
                </div>
                <div class="op-pr" v-if="opType == 0">
                    <el-button type="primary" @click="login(formData)">登陆</el-button>
                </div>      
            </el-form>
        </div>
        </Dialog>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter,useRoute } from "vue-router";
import { userRegisterApi,loginApi } from "@/api";
import {useStore} from 'vuex'
// import Message from "@/http/Message";

const { proxy } = getCurrentInstance();
const router =useRouter();
const route = useRoute();
const store = useStore();


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

const closeDialog=()=>{
  dialogConfig.show=false;
  store.commit("showLogin",false)
}



const checkUpassword = (rule,value,callback)=>{
  if(value!==formData.value.password){
   // console.log("222");
    callback(new Error(rule.message));
  }
  else{
   // console.log("333");
    callback();
  }
}

const formData = ref({
  // username:"",
  // password:"",
  // upassword:"",
  // phone:"",
  // name:"",
});
const formDataRef = ref();
const rules = {
  phone:[{required: true,message:"请输入手机号",trigger:"blur"},
            {validator:proxy.Verify.phone,message:"请输入正确的手机号",trigger:"blur"}],
  username: [{ required: true, message: "请输入用户名",trigger:"blur"},
            {min: 5,message:"用户名过短,至少为5个字符",trigger:"blur"},
            {validator:proxy.Verify.username,message:"用户名不符合规范",trigger:"blur"}],
  password:[{required: true,message:"请输入密码",trigger:"blur"},
            {validator:proxy.Verify.password,message:"请输入至少6位字母数字或字符",trigger:"blur"}],
  upassword:[{required: true,message:"请再次输入密码",trigger:"blur"},
            {validator:checkUpassword,message:"两次输入密码不一致",trigger:"blur"}],
  
  name:[{required: true,message:"请输入昵称",trigger:"blur"},],

};


const resetForm = ()=>{
  dialogConfig.show=true;
  if(opType.value == 1){
      dialogConfig.title = "注册";
    }
    else{
      dialogConfig.title = "登陆";
    }
  nextTick(()=>{
    formDataRef.value.resetFields();
    formData.value={
    }
  })
}

//点击注册时进行表单校验
const submitForm = async (formEl) => {
  // console.log(!formEl)
  if(!formEl) return

  await formEl.validate((valid) => {
    // console.log(valid)
    if (valid) {
     // console.log('submit!')
      register(formData.value);
      
    } else {
      //console.log('error submit!')
      return false
    }
  })
}

//注册请求
const register = (formData) =>{
  // userRegisterApi({username:formData.username,
  //                 password:formData.password,
  //                 name:formData.name,
  //                 phone:formData.phone}).then(res=>{
  //                   const data =res.data;
  //                   console.log(data);
  //                 })
    userRegisterApi(formData).then(res=>{
      const data =res.data;
      if(data.code == 200){
        ElMessage({
                message: '注册成功!',
                type: 'success',
              })
      // Message.success("注册成功！");
      showPanel(0);
      }
      else{
        ElMessage({
                message: data.msg,
                type: 'error',
              })
      }
    })

}
//登陆请求
const login = (formData) =>{
  loginApi({username:formData.username,
                  password:formData.password,
                  }).then(res=>{
                    const data =res.data;
                    if(data.code === 200){
                      dialogConfig.show=false;
                      const loginInfo ={
                        username:formData.username,
                        password:formData.password,
                        id:data.data.id,
                      }
                      proxy.VueCookies.set("loginInfo",loginInfo,"7d");
                      store.commit("updateLoginUserInfo",data.data);
                      location.reload();
                    }
                    else{
                      proxy.Message.error(data.msg);
                    }                  
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