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
              :key="opType"
              :model="formData"
              :rules="opType==1?rules1:(opType==2?rules2:rules0)"
              ref="formDataRef"
              label-width="100px"
              @submit.prevent
            >
            <!--input输入-->
            <!-- 注册1登陆0 -->
              <el-form-item label="用户名" prop="username" v-if="opType !== 2">
                <el-input placeholder="请输入用户名" v-model.trim="formData.username" maxLength="18"></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="email" v-if="opType !== 0">
                <div class="email-panel">
                <el-input placeholder="请输入邮箱" v-model.trim="formData.email"></el-input>
                <el-button type="primary" @click="codeClickHandle()" v-if="sendCodeInfo.sendFlag" class="email-button">获取验证码</el-button>
                <el-button type="primary" v-if="!sendCodeInfo.sendFlag" class="email-button">{{ sendCodeInfo.time+'秒后再次获取' }}</el-button>
              </div>
              </el-form-item>    
              <el-form-item label="邮箱验证码" prop="code" v-if="opType !== 0">
                <el-input placeholder="请输入邮箱验证码" v-model.trim="formData.code"></el-input>
              </el-form-item> 
              <el-form-item :label="opType==2?'新密码':'密码'" prop="password">
                <el-input show-password placeholder="请输入密码" v-model.trim="formData.password" type="password"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="upassword" v-if="opType !== 0">
                <el-input show-password placeholder="请确认密码" v-model.trim="formData.upassword" type = "password"></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="name" v-if="opType == 1" >
                <el-input placeholder="请输入昵称" v-model.trim="formData.name" maxLength="15"></el-input>
              </el-form-item>
              
              <el-form-item label="手机号" prop="phone" v-if="opType == 1" >
                <el-input placeholder="请输入手机号" v-model.trim="formData.phone" maxLength="11"></el-input>
              </el-form-item>                       
              <el-form-item label="验证码" prop="verifyCode">
                <div class="verify-code-panel">
                <el-input placeholder="请输入验证码" v-model.trim="formData.verifyCode"></el-input>
                <img :src = "codeImg" class="code-info" @click="verifyCodeChange()"/>
                </div>
              </el-form-item> 
              <el-form-item label-width="0" v-if="opType ==1">
                    <a href="javascript:;" @click="showPanel(0)" class="option">已有账号?</a>
              </el-form-item>
              <el-form-item label-width="0" v-if="opType == 0">
                <div class="login-option">
                    <a href="javascript:void(0)" @click="showPanel(1)" class="option">没有账号?</a>
                  <a href="javascript:;" @click="showPanel(2)" class="option">忘记密码?</a>
              </div>
              </el-form-item>
              <el-form-item label-width="0" v-if="opType==2">
                    <a href="javascript:;" @click="showPanel(0)" class="option">去登录?</a>
              </el-form-item>
              <el-form-item label-width="0">
                <div class="op-pr" v-if="opType == 1">
                    <el-button type="primary" @click="submitForm(formDataRef)">注册</el-button>
                </div>
                <div class="op-pr" v-if="opType == 0">
                    <el-button type="primary" @click="login(formData)">登陆</el-button>
                </div> 
                <div class="op-pr" v-if="opType == 2">
                    <el-button type="primary" @click="resetPwd()">重置密码</el-button>
                </div> 
              </el-form-item>     
            </el-form>
        </div>
        </Dialog>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter,useRoute } from "vue-router";
import { userRegisterApi,loginApi, sendHtmlMailApi,repeatCheckApi, resetPasswordApi} from "@/api";
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
    verifyCodeChange();
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


const formData = ref({});
const formDataRef = ref();

const checkRepeat=(type,rule,value,callback)=>{
  repeatCheckApi(type,value).then(res=>{
    const data = res.data;
    if(data.code==200){
      callback();
    }
    else{
      callback(new Error(rule.message));
    }
  })
}
const checkUsernameRepeat=(rule,value,callback)=>{
  checkRepeat(1,rule,value,callback)
}
const checkEmailRepeat=(rule,value,callback)=>{
  checkRepeat(2,rule,value,callback)
}

const rules1 = {
  phone:[{required: true,message:"请输入手机号",trigger:"blur"},
            {validator:proxy.Verify.phone,message:"请输入正确的手机号",trigger:"blur"}],
  username: [{ required: true, message: "请输入用户名",trigger:"blur"},
            {min: 5,message:"用户名过短,至少为5个字符",trigger:"blur"},
            {validator:proxy.Verify.username,message:"用户名不符合规范",trigger:"blur"},
            {validator:checkUsernameRepeat,message:"用户名已存在",trigger:"blur"}
          ],
  password:[{required: true,message:"请输入密码",trigger:"blur"},
            {validator:proxy.Verify.password,message:"请输入至少6位字母数字或字符",trigger:"blur"}],
  upassword:[{required: true,message:"请再次输入密码",trigger:"blur"},
            {validator:checkUpassword,message:"两次输入密码不一致",trigger:"blur"}],
  email:[{required: true,message:"请输入邮箱",trigger:"blur"},
            {validator:proxy.Verify.email,message:"邮箱格式错误",trigger:"blur"},
            {validator:checkEmailRepeat,message:"邮箱已被注册",trigger:"blur"}],
  name:[{required: true,message:"请输入昵称",trigger:"blur"},],
  code:[{required: true,message:"请输入邮箱验证码",trigger:"blur"},],
  verifyCode:[{required: true,message:"请输入验证码",trigger:"blur"},],

};

const rules0 = {
  username: [{ required: true, message: "请输入用户名",trigger:"blur"},],
  password:[{required: true,message:"请输入密码",trigger:"blur"},],
  verifyCode:[{required: true,message:"请输入验证码",trigger:"blur"},],
}


const rules2 = {
  email:[{required: true,message:"请输入邮箱",trigger:"blur"},
            {validator:proxy.Verify.email,message:"邮箱格式错误",trigger:"blur"},],
  code:[{required: true,message:"请输入邮箱验证码",trigger:"blur"},],
  password:[{required: true,message:"请输入密码",trigger:"blur"},],

  upassword:[{required: true,message:"请再次输入密码",trigger:"blur"},
            {validator:checkUpassword,message:"两次输入密码不一致",trigger:"blur"}],
  verifyCode:[{required: true,message:"请输入验证码",trigger:"blur"},],
}




const resetForm = ()=>{
  dialogConfig.show=true;
  if(opType.value == 1){
      dialogConfig.title = "注册";
    }
    else if(opType.value == 0){
      dialogConfig.title = "登陆";
    }
    else if(opType.value == 2){
      dialogConfig.title = "忘记密码";
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
const login =async (formData) =>{
  await formDataRef.value.validate((valid)=>{
    if(!valid){
      return;
    }
    else{
      loginApi(formData).then(res=>{
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
  }) 
}


const resetPwd= async()=>{
  await formDataRef.value.validate((valid)=>{
    if(!valid){
      return;
    }
    else{
      resetPasswordApi(formData.value).then(res=>{
        const data = res.data;
        if(data.code==200){
          proxy.Message.success("重置密码成功！");
          showPanel(0);
        }
        else{
          proxy.Message.error(data.msg);
        }
      })
    }
  })
  
}


//发送倒计时
const sendCodeInfo = ref({
  time:60,
  sendFlag:true
})
const sendCount=()=>{
  sendCodeInfo.value.sendFlag=false;
  const interval = window.setInterval(()=>{
    if((sendCodeInfo.value.time--)<=0){
      sendCodeInfo.value.time=60;
      sendCodeInfo.value.sendFlag=true;
      window.clearInterval(interval);
    }
  },1000)
}


//获取邮箱验证码
const codeClickHandle= async()=>{
await formDataRef.value.validateField(["email"],(valid)=>{
  if(!valid){
    return;
  }
  else{
    sendCount();
    sendHtmlMailApi({sendTo:formData.value.email}).then(res=>{
    const data =res.data;
    if(data.code==200){
      return;
    }
    else{
      proxy.Message.error(data.msg);
    }
  })
  }
})
  
}

//验证码图片
const codeImg = ref(window.env.BaseURL+"/verify/get");

const verifyCodeChange = ()=>{
  codeImg.value = window.env.BaseURL+"/verify/get"+"?time="+new Date().getTime();
}


</script>


<style lang="scss">
    .login-register{      
        display: flex;
        .email-panel{
          .email-button{
            margin-top: 3px;
          }
        }
        .verify-code-panel{
          display: flex;
          .code-info{
            margin-left: 5px;
            cursor: pointer;
          }
        }
        .op-pr{
            display: flex;
            margin:0 auto;
            justify-content:center;
        }
        .login-option{
          display: flex;
          width: 100%;
          justify-content: space-between;
        }
        .option{
          color: blue;
        }
        
    }

    
</style>