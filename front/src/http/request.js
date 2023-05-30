import store from "@/store";
import axios from "axios";
import { Promise } from "core-js";


// import { ElLoading,configProviderContextKey } from "element-plus";

// import Message from "./Message";

// const contentTypeForm = "application/x-www-form-urlencoded;charset=UTF-8";
// const contentTypeJson = "application/json"

const request = axios.create({
    baseURL:window.env.BaseURL,
    timeout:10*1000
})

//简单版
request.interceptors.request.use(config => {
    const token =  localStorage.getItem("token");
    config.headers.Authorization = "Bearer "+token;
    return config;
  }, error => {
    return Promise.reject(error);
  });
  
  request.interceptors.response.use(response => {
    const {authorization} = response.headers
    authorization && localStorage.setItem("token",authorization);
    const data = response.data;
    if(data.code ==901){
      store.commit("showLogin",true);
      store.commit("updateLoginUserInfo",null);
      return Promise.reject(alert("请登陆!"))
    }
    return response;
  }, error => {
    return Promise.reject(error);
  });


//复杂版
// const instance = axios.create({
//   baseURL:window.env.BaseURL,
//   timeout:10*1000
// })

// let loading =null;

// instance.interceptors.request.use((config)=>{
//     if(config.showLoading){
//         loading = ElLoading.service({
//             lock:true,
//             text:"加载中.......",
//             background:'rgba(0,0,0,0.7)'

//         })
//     }
//     return config;
// },(error)=>{
//     if(error.config.showLoading && loading){
//        loading.close();
//     }
//     Message.error("请求发送失败");
//     return Promise.reject("请求发送失败");
// });

// instance.interceptors.response.use(
//     (response)=>{
//         const {showLoading,errorCallback,showError} =response.config;
//         if(showLoading&&loading){
//             loading.close();
//         }
//         const responseData = response.data;
//         if(response.code == 200){
//           return responseData;
//         }
//         else{
//           if(errorCallback){
//             errorCallback(responseData);
//           }
//           return Promise.reject({showError:showError,msg:responseData.msg})
//         }
//     },(error)=>{
//       if(error.config.showLoading&&loading){
//         loading.close();
//       }
//       return Promise.reject({showError:true,msg:"网络异常"});
//     }
// );

// const request = (config)=>{
//     const {url,formData,showLoading =true,errorCallback,showError = true,method} =config
//     let contentType = contentTypeJson;
//     // for(let key in params){
//     //     fromData.append(key,params[key]==undefined?"":params[key]);
//     // }
//     // if(dataType!=null&&dataType==="json" ){
//     //     contentType =contentTypeJson;
//     // }
//     let headers = {
//         'Content-Type':contentType,
//         'X-Requested-With':'XMLHttpRequest',
//     }
//     if(method==="post"){
//       return instance.post(url,formData,{
//           headers:headers,
//           showLoading:showLoading,
//           errorCallback:errorCallback,
//           showError:showError
//       }).catch(error =>{
//           if(error.showError){
//               Message.error(error.msg);
//           }
//           return null;
//       })
//   }
// }

export default request;