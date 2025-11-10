<template>
  <div class="register-container">
    <h2>自助开户</h2>

    <input v-model="name" placeholder="请输入姓名">

    <select v-model="sex">
      <option value="男">男</option>
      <option value="女">女</option>
    </select>

    <input type="password" v-model="password" placeholder="请输入密码">
    <input type="password" v-model="confirmPassword" placeholder="请再次确认密码">

    <input type="number" v-model="balance" placeholder="请输入存款金额">
    <input type="number" v-model="limit" placeholder="请输入每日取款限额">

    <button @click="register">开户</button>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      name: '',
      password: '',
      confirmPassword: '',
      balance: 0,
      limit: 1000,
      sex: '男',
      errorMessage: ''
    }
  },
  methods: {
    async register() {
      try {
        const res = await axios.post('http://localhost:8090/api/atm/open', null, {
          params: {
            name: this.name,
            password: this.password,
            balance: this.balance,
            limit: this.limit,
            sex: this.sex
          }
        });

        if (res.data && res.data.card) {
          localStorage.setItem('account', JSON.stringify(res.data));
          this.$router.push('/home');
        } else {
          this.errorMessage = "开户失败，请检查信息是否填写正确";
        }
      } catch (e) {
        this.errorMessage = "服务器连接失败，请检查后端是否运行";
      }
    }
  }
}
</script>



