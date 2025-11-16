<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>取款</h2>

      <input class="input" v-model="amount" placeholder="金额"/>
      <input class="input" type="password" v-model="password" placeholder="密码"/>
      <button class="btn" @click="doWithdraw">确认取款</button>

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
      amount:'',
      password:'',
      msg:''
    }
  },
  methods:{
    async doWithdraw(){
      this.msg=''

      const acc = JSON.parse(localStorage.getItem("account"))

      try{
        const res = await axios.post("http://localhost:8090/api/atm/withdraw", {
          card: acc.card,
          amount: Number(this.amount),
          password: this.password
        })
        if(res.data){
          this.msg="取款成功！"
        }
      }catch(e){
        this.msg = e.response?.data || "服务器错误"
      }
    }
  }
}
</script>
