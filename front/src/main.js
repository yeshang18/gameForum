import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'
import "@/assets/icon/iconfont.css"

import Verify from './utils/Verify'
import Message from './http/Message'
import Util from './utils/Util'


//全局组件
import Avatar from './components/Avatar.vue'
import Dialog from "./components/Dialog.vue"
import DataList from './components/DataList.vue'
import EditorHtml from './components/EditorHtml.vue'
import EditorMarkdown from './components/EditorMarkdown.vue'
import CoverUpload from './components/CoverUpload.vue'


const app = createApp(App)

app.use(store).use(router).mount('#app')

app.config.globalProperties.VueCookies = VueCookies;

app.component("Dialog",Dialog);
app.component("Avatar",Avatar);
app.component("DataList",DataList);
app.component("EditorMarkdown",EditorMarkdown);
app.component("EditorHtml",EditorHtml);
app.component("CoverUpload",CoverUpload);

app.config.globalProperties.Verify=Verify;
app.config.globalProperties.Message =Message;
app.config.globalProperties.Util =Util;
app.config.globalProperties.$ImgUrl = "http://localhost:8080//images/";
