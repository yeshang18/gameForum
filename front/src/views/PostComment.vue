<template>
  <div class="post-form-panel">
        <Avatar :width="avatarWidth" :userId="userId"></Avatar>
        <div class="comment-form">
            <el-form
              :model="formData"
              :rules="rules"
              ref="formDataRef"
              @submit.prevent       
            >
            <!--textarea输入-->
            <el-form-item prop="content">
                <el-input
                  :placeholder="placeholderInfo"
                  type="textarea"
                  :maxlength="690"
                  resize="none"
                  v-model.trim="formData.content"
                  :validate-event="false"
              ></el-input>
              </el-form-item>
            </el-form>
        </div>
        <div class="send-btn" @click="sendCommentHandle()">发表</div>
   </div>
</template>

<script setup>
import { ref,  getCurrentInstance, nextTick, } from "vue"
import { useStore } from "vuex";
import { saveCommentApi } from "@/api";

const { proxy } = getCurrentInstance();
const store = useStore();

const props =defineProps({
    avatarWidth:{
        type:Number,
    },
    userId:{
        type:Number
    },
    articleId:{
        type:Number
    },
    commentId:{
        type:Number
    },
    placeholderInfo:{
        type:String
    },
    commentType:{ 
        type:Number
    },
    replyUserId:{
        type:Number
    }
})


const formData = ref({});
const formDataRef = ref();
const rules = {
    content:[{required: true,message:"请输入评论内容",trigger:"blur"},],
};

const submitForm = async (formEl) => {
  if(!formEl){return true;}
  await formEl.validate((valid) => {
    if (valid) {
    //  console.log('submit!')
     return false;
      
    } else {
    //   console.log('error submit!')
      return true;
    }
  })
}
//重置表单
const resetForm = ()=>{
  nextTick(()=>{
    formDataRef.value.resetFields();
    formData.value={
    }
  })
}

const emit = defineEmits(["postFinish"]);

//发送评论 1文章0评论
const sendCommentHandle=async()=>{
    if(!store.getters.getLoginUserInfo){
        store.commit("showLogin",true);
        return;
    }
    if(formData&&await submitForm(formDataRef.value)){
        proxy.Message.error("你还没有输入评论！")
        return;
    }
    formData.value.replyUserId = props.replyUserId;
    if(props.commentType==1){
        formData.value.articleId=props.articleId;
        saveCommentApi(formData.value).then(res=>{
            const data = res.data;
            if(data.code==200){
                resetForm();
                emit("postFinish",data.data);
                proxy.Message.success("发送成功！");
            }
            else
            {
                proxy.Message.error(data.msg);
            }
        }
        )
    }
    else if(props.commentType==2){
        formData.value.commentId=props.commentId;
        saveCommentApi(formData.value).then(res=>{
            const data = res.data;
            if(data.code==200){
                resetForm();
                emit("postFinish",data.data);
                proxy.Message.success("发送成功！");
            }
            else
            {
                proxy.Message.error(data.msg);
            }
        }
        )
    }

}
</script>

<style lang="scss">
.post-form-panel{
        display: flex;
        align-items: top;
        .comment-form{
            flex: 1;
            margin: 0 10px;
            .el-textarea__inner{
                height: 60px;
            }
        }
        .send-btn{
            width: 60px;
            height: 60px;
            background: rgb(0,174,236);
            color: #fff;
            text-align: center;
            line-height: 60px;
            border-radius: 5px;
            cursor: pointer;
        }
    }
</style>
