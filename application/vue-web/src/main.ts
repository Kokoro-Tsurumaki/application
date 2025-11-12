import './style.css'
import { createApp } from 'vue'

import Application from './App.vue'
import router from './router'

const app = createApp(Application)

app.use(router)

app.mount('#app')
