<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>存款</h2>

      <input class="input" v-model="amount" placeholder="请输入金额"/>
      <button class="btn" @click="doDeposit">确认存款</button>

      <p v-if="msg" style="margin-top:10px">{{msg}}</p>

      <button class="btn secondary" @click="$router.push('/home')">返回</button>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import axios from 'axios'

export default {
  components:{ NavBar },
  data(){
    return{ amount:'', msg:'' }
  },
  methods:{
    async doDeposit(){
      this.msg=''
      const acc = JSON.parse(localStorage.getItem("account"))

      try{
        const res = await axios.post("http://localhost:8090/api/atm/deposit", {
          card: acc.card,
          amount: Number(this.amount)
        })
        if(res.data){
          this.msg="存款成功！"
        }
      }catch(e){
        this.msg="服务器错误"
      }
    }
  }
}
</script>
