<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>账户信息</h2>

      <p>姓名：{{ info.name }}</p>
      <p>卡号：{{ info.card }}</p>
      <p>余额：{{ info.balance }} 元</p>
      <p>每日限额：{{ info.limit }} 元</p>
      <p>性别：{{ info.sex }}</p>

      <button class="btn" @click="$router.push('/home')">返回首页</button>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';

export default {
  components:{NavBar},
  data(){
    return{
      info:{}
    }
  },
  async created(){
    const acc = JSON.parse(localStorage.getItem("account"));
    const r = await axios.get("http://localhost:8090/api/atm/info", {
      params:{ card: acc.card }
    });
    this.info = r.data;
  }
}
</script>
