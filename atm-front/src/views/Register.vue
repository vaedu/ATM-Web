<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:560px;margin:20px auto">
      <div class="h1">自助开户</div>
      <div style="margin-top:12px;display:grid;gap:10px">
        <input class="input" v-model="name" placeholder="姓名"/>
        <input class="input" v-model="password" placeholder="6位数字密码" />
        <input class="input" v-model.number="balance" type="number" min="0" placeholder="初始存款（可为0）" />
        <select class="input" v-model="sex">
          <option>男</option><option>女</option>
        </select>
        <div style="display:flex;gap:8px;justify-content:flex-end">
          <button class="btn" @click="onRegister">开户</button>
          <router-link to="/login" class="btn secondary">返回登录</router-link>
        </div>
        <p v-if="err" style="color:var(--danger)">{{err}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { name:'', password:'', balance:0, sex:'男', err:'' } },
  methods:{
    async onRegister(){
      this.err='';
      if(!this.name || !this.password){ this.err='请填写姓名与密码'; return; }
      if(!/^\d{6}$/.test(this.password)){ this.err='密码必须为6位数字'; return; }
      try{
        const res = await axios.post('http://localhost:8090/api/atm/open', null, { params:{
            name:this.name, password:this.password, balance:this.balance, limit:1000, sex:this.sex
          }});
        // 后端返回 Result.success(created) 或者直接返回 account
        const data = res.data && res.data.data ? res.data.data : res.data;
        if(data && data.card){
          // 弹窗提示卡号（浏览器原生）
          alert('开户成功！您的卡号：' + data.card);
          localStorage.setItem('account', JSON.stringify(data));
          this.$router.push('/home');
        } else {
          this.err='开户失败，请检查信息';
        }
      }catch(e){ this.err='服务器连接失败' }
    }
  }
}
</script>
