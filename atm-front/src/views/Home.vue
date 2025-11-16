<template>
  <div class="container">
    <NavBar/>

    <div class="grid">

      <div class="panel">
        <h2>账户信息</h2>
        <p>姓名：{{ info.name }}</p>
        <p>卡号：{{ info.card }}</p>
        <p>余额：{{ info.balance }} 元</p>
        <p>每日限额：{{ info.limit }} 元</p>
        <p>性别：{{ info.sex }}</p>
      </div>

      <div class="panel">
        <h2>快速操作</h2>
        <button @click="$router.push('/deposit')" class="btn">存款</button>
        <button @click="$router.push('/withdraw')" class="btn">取款</button>
        <button @click="$router.push('/transfer')" class="btn">转账</button>
        <button @click="$router.push('/change-password')" class="btn">修改密码</button>
      </div>

      <div class="panel">
        <h2>最近交易记录</h2>
        <TransactionList :list="records"/>
      </div>

    </div>

  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import TransactionList from '@/components/TransactionList.vue'
import axios from 'axios'

export default {
  components:{NavBar, TransactionList},
  data(){
    return{
      info:{},
      records:[]
    }
  },
  async created(){
    const acc = JSON.parse(localStorage.getItem("account"))
    if(!acc){
      this.$router.push("/")
      return
    }

    // 获取账户信息
    const r1 = await axios.get("http://localhost:8090/api/atm/info", {
      params:{ card: acc.card }
    })
    this.info = r1.data

    // 最近交易
    const r2 = await axios.get("http://localhost:8090/api/atm/transactions", {
      params:{ card: acc.card }
    })
    this.records = r2.data
  }
}
</script>

<style scoped>
.grid{
  display:grid;
  gap:20px;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  margin-top:20px;
}
.panel{
  background:white;
  border-radius:12px;
  padding:20px;
  box-shadow: 0 4px 18px rgba(0,0,0,0.06);
}
.btn{
  margin:6px 0;
  width:100%;
}
</style>
