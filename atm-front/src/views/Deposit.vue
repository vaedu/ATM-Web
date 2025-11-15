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
    async doDeposit() {
      this.err = '';
      this.success = '';

      if (this.amount <= 0) {
        this.err = "请输入正确的金额";
        return;
      }

      const acc = JSON.parse(localStorage.getItem('account'));
      if (!acc) {
        this.err = "未登录，请重新登录";
        return;
      }

      try {
        const res = await axios.post("http://localhost:8090/api/atm/deposit", {
          card: acc.card,
          amount: this.amount
        });

        this.success = "存款成功！当前余额：" + res.data;
      } catch (e) {
        this.err = "服务器错误：" + (e.response?.data || '');
      }
    }

  }
}
</script>
