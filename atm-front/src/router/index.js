import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Register from "../views/Register.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/login' }, // 默认跳转到登录
        { path: '/login', component: Login },
        { path: '/home', component: Home },
        { path: '/register', component: Register }
    ]
})

export default router
