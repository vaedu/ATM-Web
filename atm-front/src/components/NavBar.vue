<template>
  <nav class="navbar">
    <div class="logo">ATM 自助服务</div>

    <div class="nav-right">
      <router-link to="/home" class="nav-item">首页</router-link>
      <router-link to="/info" class="nav-item">账户</router-link>
      <span class="nav-item" @click="logout">退出登录</span>
    </div>
  </nav>
</template>

<script>
export default {
  methods:{
    logout(){
      localStorage.removeItem('account');
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.navbar {
  height: 56px;
  background: #1a73e8;
  color: white;
  display:flex;
  align-items:center;
  justify-content:space-between;
  padding: 0 20px;
  border-radius: 8px;
  margin: 12px;
}

.logo {
  font-weight: bold;
  font-size: 18px;
}

.nav-right {
  display: flex;
  gap: 20px;
}

.nav-item {
  color: white;
  text-decoration: none;
  cursor: pointer;
  font-size: 15px;
}

.nav-item:hover {
  text-decoration: underline;
}
</style>
