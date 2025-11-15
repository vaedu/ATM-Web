<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="margin-top:8px">
      <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;flex-wrap:wrap">
        <div>
          <div class="h1">欢迎回来，{{ user ? user.name : '用户' }}</div>
          <div style="color:var(--muted);margin-top:6px">卡号：{{ maskCard(user?.card) }}</div>
        </div>
        <div style="text-align:right">
          <div style="font-weight:800;font-size:20px">{{ user ? Number(user.balance).toFixed(2) : '0.00' }} 元</div>
          <div style="color:var(--muted)">本日限额：{{ user ? Number(user.limit).toFixed(2) : '0.00' }} 元</div>
        </div>
      </div>
    </div>

    <div class="home-content" style="margin-top:18px">
      <div class="action-card" @click="$router.push('/deposit')">存款</div>
      <div class="action-card" @click="$router.push('/withdraw')">取款</div>
      <div class="action-card" @click="$router.push('/transfer')">转账</div>
      <div class="action-card" @click="$router.push('/info')">账户信息</div>
    </div>

    <div class="record-section">
      <TransactionList :list="records" @refresh="loadRecords"/>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import TransactionList from '@/components/TransactionList.vue';
import axios from 'axios';
export default {
  components:{NavBar, TransactionList},
  data(){ return { user:null, records:[] } },
  created(){
    const s = localStorage.getItem('account');
    this.user = s ? JSON.parse(s) : null;
    if(!this.user) this.$router.push('/login');
    else this.loadRecords();
  },
  methods:{
    maskCard(c){ if(!c) return ''; const s = String(c); return s.slice(0,4) + ' **** ' + s.slice(-4); },
    async loadRecords(){
      if(!this.user) return;
      try{
        const res = await axios.get('http://localhost:8090/api/transactions/list',{ params:{ card:this.user.card }});
        this.records = (res.data || []).slice(0,5);
      }catch(e){
        console.error('加载交易记录失败：', e);
        this.records = [];
      }
    }
  }
}
</script>

<style scoped>
.home-content{ display:grid; grid-template-columns:repeat(2,1fr); gap:18px; margin-top:20px }
@media(max-width:600px){ .home-content{ grid-template-columns:1fr } }
</style>
