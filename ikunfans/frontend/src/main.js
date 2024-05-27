import { createApp } from 'vue';
import App from './App.vue';
import components from './components/global';
import Router from './router/index'
import './style/app.css'
import {pinia} from '@/store'
import ArcoVue from "@arco-design/web-vue";
import '@arco-design/web-vue/dist/arco.css';
import ArcoVueIcon from '@arco-design/web-vue/es/icon'
import './style/login.css'
import { Message } from '@arco-design/web-vue';

const app = createApp(App)
app.config.productionTip = false

for (const i in components) {
  app.component(i, components[i])
}
app.component("HomePage", () => import("@/components/home-page/index.vue"));
app.component("AccountPage", () => import("@/components/account-page/index.vue"));
app.component("SettingPage", () => import("@/components/setting-page/index.vue"));
app.component("LoginPage", () => import("@/components/login-page/index.vue"));
app.component("DetailPage", () => import("@/components/detail-page/index.vue"));

app.use(pinia)
app.use(Router)
app.use(ArcoVue)
app.use(ArcoVueIcon)
Message._context = app._context;
app.mount('#app')
