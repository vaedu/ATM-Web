<!-- src/views/Home.vue -->
<template>
  <div class="home">
    <NavBar />
    <h2>欢迎回来，{{ user ? user.name : '用户' }}</h2>

    <div class="records">
      <h3>最近交易记录</h3>
      <button @click="fetchRecords">刷新</button>
      <table v-if="records.length > 0">
        <thead>
        <tr>
          <th>时间</th>
          <th>类型</th>
          <th>金额</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, i) in records" :key="i">
          <td>{{ r.date }}</td>
          <td>{{ r.type }}</td>
          <td>{{ r.amount }}</td>
        </tr>
        </tbody>
      </table>
      <p v-else>暂无交易记录。</p>
    </div>
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
      records: []
    };
  },
  mounted() {
    this.fetchRecords();
  },
  methods: {
    async fetchRecords() {
      try {
        const res = await axios.get(`http://localhost:8090/api/atm/records`, {
          params: { card: this.user.card }
        });
        this.records = res.data || [];
      } catch (e) {
        console.error(e);
        alert("获取交易记录失败！");
      }
    }
  }
};
</script>

<style scoped>
.home {
  text-align: center;
  padding: 20px;
}
.records {
  margin-top: 30px;
}
table {
  margin: 0 auto;
  border-collapse: collapse;
  width: 70%;
}
th, td {
  border: 1px solid #ccc;
  padding: 8px;
}
</style>
