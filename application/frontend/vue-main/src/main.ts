import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import  { createPersistedState } from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'

const pinia = createPinia()
pinia.use(createPersistedState({
  storage: localStorage,
  auto: true,
}))

const app = createApp(App)

app.use(pinia)
app.use(router)

app.mount('#app')
