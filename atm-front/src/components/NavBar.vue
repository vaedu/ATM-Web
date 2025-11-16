<template>
  <nav class="nav">
    <div class="left" @click="$router.push('/home')">🏦 ATM 系统</div>

    <div class="right">
      <span v-if="account">卡号：{{ account.card }}</span>
      <button class="logout" @click="logout">退出</button>
    </div>
  </nav>
</template>

<script>
export default {
  computed:{
    account(){
      const a = localStorage.getItem("account");
      return a ? JSON.parse(a) : null;
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("account");
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>
.nav{
  height: 60px;
  background: var(--theme);
  color:white;
  display:flex;
  justify-content:space-between;
  align-items:center;
  padding: 0 16px;
  font-size:18px;
}
.left{
  cursor:pointer;
  font-weight: bold;
}
.right{
  display:flex;
  gap:12px;
  align-items:center;
}
.logout{
  background:white;
  color:var(--theme);
  padding:6px 10px;
  border-radius:6px;
  border:none;
}
</style>
