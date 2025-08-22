import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import tenderApi from './api/tenderApi'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 添加API到全局属性
app.config.globalProperties.$api = tenderApi
store.$api = tenderApi

app.use(store)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
