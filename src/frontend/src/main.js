import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
const app = createApp(App)
import "./assets/tailwind.css";
app.use(router).use(store).mount('#app')





