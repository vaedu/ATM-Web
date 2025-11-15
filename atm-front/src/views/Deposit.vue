<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:520px;margin:20px auto">
      <div class="h1">存款</div>
      <input class="input" v-model.number="amount" type="number" min="0.01" placeholder="金额 (元)"/>
      <div style="height:14px"></div>
      <button class="btn" @click="doDeposit">确认存款</button>
      <p v-if="msg" :style="{color: msgErr? 'var(--danger)':'var(--success)'}">{{msg}}</p>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { amount:null, msg:'', msgErr:false, user: JSON.parse(localStorage.getItem('account')) } },
  methods:{
    async doDeposit(){
      this.err = '';
      if (!this.card || !this.amount) {
        this.err = '请填写卡号和存款金额';
        return;
      }

      try {
        const res = await axios.post('http://localhost:8090/api/atm/deposit',
            {
              card: this.card,
              amount: this.amount
            },
            {
              headers: {
                'Content-Type': 'application/json'
              }
            });

        if (res.data) {
          this.$router.push('/home');  // 存款成功后跳转到首页
        } else {
          this.err = '存款失败，请重试';
        }
      } catch (e) {
        this.err = '服务器连接失败';
      }
    }
  }
}
</script>
