<template>
  <header class="nav" :class="{compact: compact}">
    <div class="nav-left">
      <div class="brand">
        <div class="logo" aria-hidden></div>
        <div class="brand-text">
          <div class="bank-name">银行虚拟 ATM</div>
          <div class="bank-sub">个人网银演示</div>
        </div>
      </div>
    </div>

    <div class="nav-right">
      <div class="user" v-if="user">
        <div class="user-name">欢迎，{{ shortName(user.name) }}</div>
        <button class="btn ghost" @click="goInfo">账户</button>
        <button class="btn secondary" @click="logout">退出</button>
      </div>
      <div v-else>
        <router-link to="/login" class="btn">登录</router-link>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "NavBar",
  data(){ return { user: null, compact:false }},
  created(){
    const s = localStorage.getItem('account');
    if(s) this.user = JSON.parse(s);
    // 小屏紧凑模式
    this.compact = window.innerWidth < 520;
    window.addEventListener('resize', ()=> this.compact = window.innerWidth < 520);
  },
  methods:{
    logout(){
      localStorage.removeItem('account');
      this.$router.push('/login');
    },
    goInfo(){ this.$router.push('/info') },
    shortName(n){
      if(!n) return '用户';
      return n.length>4 ? n.slice(0,4)+'…' : n;
    }
  }
}
</script>

<style scoped>
.nav{ display:flex; justify-content:space-between; align-items:center;
  padding:18px 0; background:var(--accent); color:#fff; border-radius:8px; margin-bottom:18px;
  box-shadow:0 8px 28px rgba(37,99,235,0.12);
}
.brand{display:flex;align-items:center;gap:12px}
.logo{width:48px;height:48px;border-radius:8px;background:linear-gradient(135deg,#ffffff30,#ffffff10);box-shadow:inset 0 1px 0 rgba(255,255,255,0.2)}
.bank-name{font-weight:800;font-size:16px}
.bank-sub{font-size:12px;opacity:.9}
.nav-right{display:flex;align-items:center;gap:12px}
.user-name{margin-right:8px;font-weight:700}
.btn{font-size:13px;padding:8px 12px;border-radius:8px}
.compact .brand-text{display:none}
@media(max-width:600px){ .nav{padding:12px} .btn{padding:6px 10px} }
</style>
