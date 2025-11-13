<!-- src/components/NavBar.vue -->
<template>
  <nav class="navbar">
    <ul>
      <li><router-link to="/home">首页</router-link></li>
      <li><router-link to="/deposit">存款</router-link></li>
      <li><router-link to="/withdraw">取款</router-link></li>
      <li><router-link to="/transfer">转账</router-link></li>
      <li><router-link to="/info">账户信息</router-link></li>
      <li><a @click="logout" href="javascript:void(0)">退出登录</a></li>
    </ul>
  </nav>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.removeItem('account');
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
.navbar {
  background: #2c3e50;
  padding: 10px 0;
  text-align: center;
}
ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
li {
  display: inline-block;
  margin: 0 15px;
}
a, router-link {
  color: white;
  text-decoration: none;
  font-weight: 600;
}
a:hover, .router-link-active {
  color: #1abc9c;
}
</style>
