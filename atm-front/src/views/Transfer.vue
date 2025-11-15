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
      this.msg=''; this.msgErr=false;
      if(!this.toCard || !this.amount){ this.msg='请填写完整信息'; this.msgErr=true; return; }
      if(!this.password){ this.msg='请输入密码'; this.msgErr=true; return; }
      try{
        const res = await axios.post('http://localhost:8090/api/atm/transfer', null, { params:{
            fromCard:this.user.card, toCard:this.toCard, amount:this.amount, password:this.password
          }});
        if(res.data === true || (res.data && res.data.success)) {
          this.msg='转账成功';
          this.user.balance = Number(this.user.balance) - Number(this.amount);
          localStorage.setItem('account', JSON.stringify(this.user));
        } else {
          this.msg='转账失败';
          this.msgErr=true;
        }
      }catch(e){ this.msg='服务器错误'; this.msgErr=true }
    }
  }
}
</script>
