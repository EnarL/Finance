import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
const app = createApp(App)
import "./assets/tailwind.css";
app.use(router).mount('#app')





