<template>
  <div class="page">
    <NavBar />
    <h2>账户取款</h2>
    <input type="number" v-model="amount" placeholder="请输入取款金额" />
    <button @click="withdraw">确认取款</button>
  </div>
</template>

<script>
import axios from "axios";
import NavBar from "@/components/NavBar.vue";

export default {
  components: { NavBar },
  data() {
    return { amount: "", user: JSON.parse(localStorage.getItem("account")) };
  },
  methods: {
    async withdraw() {
      if (this.amount <= 0) return alert("金额必须大于0！");
      try {
        const res = await axios.post("http://localhost:8090/api/atm/withdraw", null, {
          params: { card: this.user.card, amount: this.amount }
        });
        alert(res.data.message || "取款成功！");
      } catch {
        alert("操作失败！");
      }
    }
  }
};
</script>
