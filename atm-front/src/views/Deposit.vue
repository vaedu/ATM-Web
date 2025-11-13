<template>
  <div class="page">
    <NavBar />
    <h2>账户存款</h2>

    <input type="number" v-model="amount" placeholder="请输入存款金额" />
    <button @click="deposit">确认存款</button>
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
    async deposit() {
      if (this.amount <= 0) return alert("金额必须大于0！");
      try {
        const res = await axios.post("http://localhost:8090/api/atm/deposit", null, {
          params: { card: this.user.card, amount: this.amount }
        });
        alert(res.data.message || "存款成功！");
      } catch {
        alert("操作失败！");
      }
    }
  }
};
</script>

<style scoped>
.page { text-align: center; padding: 20px; }
input { margin: 10px; padding: 5px; }
</style>
