<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>转账</h2>

      <input class="input" v-model="toCard" placeholder="对方卡号"/>
      <input class="input" v-model="amount" placeholder="金额"/>
      <input class="input" type="password" v-model="password" placeholder="密码"/>

      <button class="btn" @click="doTransfer">确认转账</button>

      <p v-if="msg" style="margin-top:10px">{{msg}}</p>

      <button class="btn secondary" @click="$router.push('/home')">返回</button>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import axios from 'axios'

export default {
  components:{NavBar},
  data(){
    return{
      toCard:'', amount:'', password:'', msg:''
    }
  },
  methods:{
    async doTransfer(){
      this.msg=''
      const acc = JSON.parse(localStorage.getItem("account"))

      try{
        const res = await axios.post("http://localhost:8090/api/atm/transfer", {
          fromCard: acc.card,
          toCard: this.toCard,
          amount: Number(this.amount),
          password: this.password
        })
        if(res.data){
          this.msg="转账成功！"
        }
      }catch(e){
        this.msg = e.response?.data || "服务器错误"
      }
    }
  }
}
</script>
