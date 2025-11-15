<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:520px;margin:20px auto">
      <div class="h1">取款</div>
      <input class="input" v-model.number="amount" type="number" min="0.01" placeholder="金额 (元)"/>
      <div style="height:8px"></div>
      <input class="input" v-model="password" type="password" placeholder="请输入密码"/>
      <div style="height:14px"></div>
      <button class="btn" @click="doWithdraw">确认取款</button>
      <p v-if="msg" :style="{color: msgErr? 'var(--danger)':'var(--success)'}">{{msg}}</p>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { amount:null, password:'', msg:'', msgErr:false, user: JSON.parse(localStorage.getItem('account')) } },
  methods:{
    async doWithdraw(){
      this.err = '';
      if (!this.card || !this.amount || !this.password) {
        this.err = '请填写卡号、取款金额和密码';
        return;
      }

      try {
        const res = await axios.post('http://localhost:8090/api/atm/withdraw',
            {
              card: this.card,
              amount: this.amount,
              password: this.password
            },
            {
              headers: {
                'Content-Type': 'application/json'
              }
            });

        if (res.data) {
          this.$router.push('/home');  // 取款成功后跳转到首页
        } else {
          this.err = '取款失败，请重试';
        }
      } catch (e) {
        this.err = '服务器连接失败';
      }
    }
  }
}
</script>
