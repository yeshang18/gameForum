import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'

import Verify from './utils/Verify'
import Message from './http/Message'
import "@/assets/icon/iconfont.css"
//全局组件
import Avatar from './components/Avatar.vue'
import Dialog from "./components/Dialog.vue"
import DataList from './components/DataList.vue'

const app = createApp(App)

app.use(store).use(router).mount('#app')

app.config.globalProperties.VueCookies = VueCookies;

app.component("Dialog",Dialog);
app.component("Avatar",Avatar);
app.component("DataList",DataList);

app.config.globalProperties.Verify=Verify;
app.config.globalProperties.Message =Message;
