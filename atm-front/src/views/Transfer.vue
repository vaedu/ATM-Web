<template>
  <div class="page">
    <NavBar />
    <h2>转账</h2>
    <input v-model="target" placeholder="请输入目标卡号" />
    <input type="number" v-model="amount" placeholder="请输入转账金额" />
    <input type="password" v-model="password" placeholder="请输入密码验证" />
    <button @click="transfer">确认转账</button>
  </div>
</template>

<script>
import axios from "axios";
import NavBar from "@/components/NavBar.vue";

export default {
  components: { NavBar },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("account")),
      target: "",
      amount: "",
      password: ""
    };
  },
  methods: {
    async transfer() {
      if (!this.target || !this.amount) return alert("请填写完整信息！");
      try {
        const res = await axios.post("http://localhost:8090/api/atm/transfer", null, {
          params: {
            fromCard: this.user.card,
            toCard: this.target,
            amount: this.amount,
            password: this.password
          }
        });
        alert(res.data.message || "转账成功！");
      } catch {
        alert("操作失败！");
      }
    }
  }
};
</script>
