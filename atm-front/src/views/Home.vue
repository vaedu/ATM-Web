<template>
  <div class="container">
    <NavBar />

    <div class="card" style="max-width:900px; margin:20px auto; padding:20px">
      <h2>欢迎回来，{{ user?.name || '用户' }}</h2>

      <!-- 基本账户信息 -->
      <div class="info-grid">
        <div class="info-item">
          <span class="label">姓名：</span>
          <span>{{ user?.name }}</span>
        </div>

        <div class="info-item">
          <span class="label">性别：</span>
          <span>{{ user?.gender }}</span> <!-- FIXED -->
        </div>

        <div class="info-item">
          <span class="label">卡号：</span>
          <span>{{ maskedCard }}</span>
        </div>

        <div class="info-item">
          <span class="label">余额：</span>
          <span>{{ user?.balance }} 元</span> <!-- FIXED: 防止 NaN -->
        </div>

        <div class="info-item">
          <span class="label">今日限额：</span>
          <span>{{ user?.dailyLimit }} 元</span>
        </div>
      </div>

      <!-- 交易操作按钮 -->
      <div class="actions">
        <router-link class="btn" to="/deposit">存款</router-link>
        <router-link class="btn" to="/withdraw">取款</router-link>
        <router-link class="btn" to="/transfer">转账</router-link>
        <router-link class="btn secondary" to="/info">账户信息</router-link>
      </div>

      <!-- 最近交易 -->
      <h3 style="margin-top:30px">最近交易记录</h3>

      <div v-if="records.length === 0" class="empty">暂无交易记录</div>

      <ul v-else class="record-list">
        <li v-for="(r, index) in records" :key="index" class="record-item">
          <div class="record-type">{{ typeName(r.type) }}</div>
          <div class="record-amount">{{ r.amount }} 元</div>
          <div class="record-time">{{ r.time }}</div>
          <div class="record-remark">{{ r.remark }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import axios from "axios";

export default {
  components: { NavBar },

  data() {
    return {
      user: null,
      records: []
    };
  },

  computed: {
    // 卡号脱敏
    maskedCard() {
      if (!this.user?.card) return "";
      const c = this.user.card;
      return c.substring(0, 4) + " **** **** " + c.substring(c.length - 4);
    }
  },

  methods: {
    // 显示中文交易类型
    typeName(t) {
      const map = {
        DEPOSIT: "存款",
        WITHDRAW: "取款",
        TRANSFER_IN: "转账收入",
        TRANSFER_OUT: "转账支出"
      };
      return map[t] || t;
    },

    async loadRecords() {
      if (!this.user?.card) return;
      try {
        const res = await axios.get("http://localhost:8090/api/transactions/list", {
          params: { card: this.user.card }
        });

        this.records = res.data || [];
      } catch (e) {
        console.error("加载交易记录失败：", e);
      }
    }
  },

  created() {
    // 读取本地用户信息
    const a = localStorage.getItem("account");
    if (!a) {
      this.$router.push("/login");
      return;
    }

    this.user = JSON.parse(a);

    // 防止 NaN：确保字段存在
    if (!this.user.dailyLimit) this.user.dailyLimit = 0;
    if (!this.user.balance) this.user.balance = 0;

    this.loadRecords();
  }
};
</script>

<style scoped>
.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin: 15px 0;
}

.label {
  font-weight: bold;
  color: #444;
}

.actions {
  margin-top: 20px;
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.record-list {
  margin-top: 12px;
  padding: 0;
}

.record-item {
  list-style: none;
  padding: 10px 12px;
  border-bottom: 1px solid #ddd;
}

.empty {
  color: #777;
  padding: 8px;
}
</style>
