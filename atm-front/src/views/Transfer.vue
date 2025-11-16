<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:520px;margin:20px auto">
      <div class="h1">转账</div>
      <input class="input" v-model="toCard" placeholder="目标卡号"/>
      <div style="height:8px"></div>
      <input class="input" v-model.number="amount" type="number" placeholder="金额 (元)"/>
      <div style="height:8px"></div>
      <input class="input" v-model="password" type="password" placeholder="请输入密码"/>
      <div style="height:12px"></div>
      <button class="btn" @click="doTransfer">确认转账</button>
      <p v-if="msg" :style="{color: msgErr? 'var(--danger)':'var(--success)'}">{{msg}}</p>
      <button class="btn secondary" @click="$router.push('/home')" style="margin-top:20px">
        返回首页
      </button>

    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { toCard:'', amount:null, password:'', msg:'', msgErr:false, user: JSON.parse(localStorage.getItem('account')) } },
  methods:{
    async doTransfer(){
      this.msg='';
      this.msgErr=false;

      const acc = JSON.parse(localStorage.getItem("account"));
      if (!acc) {
        this.err = "未登录，请重新登录";
        return;
      }

      try {
        const res = await axios.post("http://localhost:8090/api/atm/transfer", {
          fromCard: acc.card,
          toCard: this.toCard,
          password: this.password,
          amount: this.amount
        });

        this.msg = "转账成功！";
      } catch(e) {
        if (e.response && e.response.data) {
          this.err = e.response.data;   // 直接显示后端的错误文本
        } else {
          this.err = "服务器错误";
        }
      }

    }
  }
}
</script>
