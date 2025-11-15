<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:500px;margin:20px auto">
      <div class="h1">用户登录</div>

      <div style="margin-top:10px">
        <input class="input" v-model="card" placeholder="请输入卡号"/>
        <div style="height:12px"></div>
        <input class="input" v-model="password" placeholder="请输入6位数字密码" type="password"/>
        <div style="height:12px"></div>
        <div style="display:flex;gap:8px;justify-content:space-between;align-items:center">
          <button class="btn" @click="onLogin">登录</button>
          <router-link to="/register" class="btn secondary">去开户</router-link>
        </div>
        <p v-if="err" style="color:var(--danger);margin-top:10px">{{err}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { card:'', password:'', err:'' } },
  methods:{
    async onLogin() {
      this.err = '';
      if (!this.card || !this.password) {
        this.err = '请填写卡号和密码';
        return;
      }
      try {
        const res = await axios.post('http://localhost:8090/api/atm/login',
            {
              card: this.card,
              password: this.password
            },
            {
              headers: {
                'Content-Type': 'application/json'
              }
            });

        if (res.data && res.data.card) {
          localStorage.setItem('account', JSON.stringify(res.data));
          this.$router.push('/home');
        } else {
          this.err = '登录失败：卡号或密码错误';
        }
      } catch (e) {
        this.err = '服务器连接失败';
      }
    }


  }
}
</script>

<style scoped>
.card{padding:22px}
</style>
