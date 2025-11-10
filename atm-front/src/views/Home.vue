<template>
  <div class="home-container">
    <h2>欢迎回来，{{ user ? user.name : '用户' }}</h2>

    <div class="menu">
      <button>查询余额</button>
      <button>存款</button>
      <button>取款</button>
      <button>转账</button>
      <button @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: null // 用来存用户信息
    }
  },
  mounted() {
    // 从 localStorage 取登录时保存的信息
    const account = localStorage.getItem('account')
    if (account) {
      this.user = JSON.parse(account)
    }
  }
}
</script>


<style scoped>
.home-container {
  text-align: center;
  margin-top: 100px;
  font-family: "Microsoft YaHei",serif;
}
.menu {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  width: 200px;
  margin-left: auto;
  margin-right: auto;
}
button {
  margin-top: 10px;
  padding: 10px;
  border-radius: 6px;
  border: none;
  background: #67c23a;
  color: white;
  cursor: pointer;
}
button:hover {
  background: #85ce61;
}
</style>
