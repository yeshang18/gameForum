import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'
import Dialog from "./components/Dialog.vue"
import Verify from './utils/Verify'

const app = createApp(App)

app.use(store).use(router).mount('#app')

app.config.globalProperties.VueCookies = VueCookies;

app.component("Dialog",Dialog);

app.config.globalProperties.Verify=Verify;