<template>
  <div class="login-container">
    <h2>ATM 登录</h2>
    <input type="text" v-model="cardNumber" placeholder="请输入卡号">
    <input type="password" v-model="password" placeholder="请输入密码">
    <button @click="login">登录</button>
    <p class="error" v-if="errorMessage">{{ errorMessage }}</p>
    <p style="margin-top: 10px;">
      还没有账号？
      <a @click="goRegister" style="cursor:pointer;color:blue;">点击自助开户</a>
    </p>

  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      cardNumber: '',
      password: '',
      errorMessage: ''
    }
  },
  methods: {
    async login() {
      try {
        const res = await axios.post('http://localhost:8090/api/atm/login', null, {
          params: {
            card: this.cardNumber,
            password: this.password
          }
        });

        if (res.data) {
          // ✅ 存入本地存储
          localStorage.setItem("account", JSON.stringify(res.data));
          this.$router.push("/home");
        } else {
          this.errorMessage = "卡号或密码错误";
        }
      } catch (err) {
        this.errorMessage = "服务器连接失败，请检查后端是否启动";
      }
    },
    goRegister() {
      this.$router.push("/register"); // 跳转到开户页面
    }
  }
}
</script>

<style>
.login-container {
  width: 300px;
  margin: 100px auto;
  text-align: center;
}
input {
  display: block;
  width: 100%;
  height: 35px;
  margin-top: 15px;
}
button {
  width: 100%;
  margin-top: 20px;
  height: 35px;
}
.error {
  margin-top: 10px;
  color: red;
}
</style>
