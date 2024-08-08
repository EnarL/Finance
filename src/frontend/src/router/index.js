import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Dashboard from '../views/Dashboard.vue';
import Register from '../views/Register.vue';
import Settings from "../views/Settings.vue";
import Monthly from "../views/Monthly.vue";
const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/settings',
        name: 'Settings',
        component: Settings
    },
    {
        path: '/Monthly',
        name: 'Monthly',
        component: Monthly
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router;