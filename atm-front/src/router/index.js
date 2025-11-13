import { createRouter, createWebHistory } from 'vue-router'

// 引入页面组件
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import Info from '../views/Info.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/login' // 默认跳转到登录页
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/home',
            name: 'Home',
            component: Home
            // 📘 交易记录部分将直接在 Home.vue 内嵌展示，不需要单独路由
        },
        {
            path: '/register',
            name: 'Register',
            component: Register
        },
        {
            path: '/info',
            name: 'Info',
            component: Info
        },
        {
            path: '/:pathMatch(.*)*', // 兜底处理不存在的路由
            redirect: '/login'
        }
    ]
})

export default router
