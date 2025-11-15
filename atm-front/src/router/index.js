import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import Info from '../views/Info.vue'
import Deposit from '../views/Deposit.vue'
import Withdraw from '../views/Withdraw.vue'
import Transfer from '../views/Transfer.vue'

const routes = [
    { path: '/', component: Login },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/home', component: Home },
    { path: '/info', component: Info },
    { path: '/deposit', component: Deposit },
    { path: '/withdraw', component: Withdraw },
    { path: '/transfer', component: Transfer }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
