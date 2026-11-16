<script setup>
import request from "@/utils/request";
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

const accounts = ref([]);
const loading = ref(false);
const busy = ref(""); // 正在操作的卡号，防止重复点击

async function loadLocked() {
  loading.value = true;
  try {
    const res = await request.get("/admin/list");
    if (res.data.success) {
      accounts.value = res.data.data || [];
    } else {
      ElMessage.error(res.data.message || "加载失败");
    }
  } catch (e) {
    ElMessage.error(e.message || "服务器错误");
    accounts.value = [];
  } finally {
    loading.value = false;
  }
}

// ===== 操作接口（均为 POST，参数 { card }，返回 data 为提示文字）=====
// 注意：锁定、取消注销两个接口路径是我假定的，后端定好后改这里即可
const ACTIONS = {
  lock:   { url: "/admin/lock",    name: "锁定",     tip: "锁定后该账户将无法登录和交易" },
  unlock: { url: "/admin/unlock",  name: "解锁",     tip: "" },
  cancel: { url: "/admin/restore", name: "取消注销", tip: "账户将恢复为正常状态" },
};

// 对某一行执行操作：先确认，再请求，成功后重新加载列表（状态会变，不能只删行）
async function doAction(row, key) {
  const act = ACTIONS[key];
  try {
    await ElMessageBox.confirm(
        `确认${act.name} ${row.name}（${row.card}）的账户吗？${act.tip}`,
        `${act.name}确认`,
        { confirmButtonText: `确认${act.name}`, cancelButtonText: "取消", type: "warning" }
    );
  } catch {
    return; // 用户取消
  }

  busy.value = row.card;
  try {
    const res = await request.post(act.url, { card: row.card });
    if (res.data.success) {
      ElMessage.success(res.data.data || `${act.name}成功`);
      await loadLocked();
    } else {
      ElMessage.error(res.data.message || `${act.name}失败`);
    }
  } catch (e) {
    ElMessage.error(e.message || "服务器错误");
  } finally {
    busy.value = "";
  }
}

// 状态展示：ACTIVE / TEST 显示正常，CANCEL 显示已注销，LOCKED 显示已锁定
function statusView(status) {
  switch (status) {
    case "ACTIVE":
    case "TEST":
      return { text: "正常", cls: "ok" };
    case "CANCEL":
      return { text: "已注销", cls: "cancel" };
    case "LOCKED":
      return { text: "已锁定", cls: "locked" };
    default:
      return { text: status || "-", cls: "" };
  }
}

onMounted(loadLocked);
</script>

<template>
  <h2 class="page-title">锁定账户</h2>

  <div class="panel" v-loading="loading">
    <table class="tbl">
      <thead>
      <tr>
        <th style="width: 190px">卡号</th>
        <th style="width: 200px">户主</th>
        <th style="width: 215px">注销时间</th>
        <th>状态</th>
        <th class="right">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="row in accounts" :key="row.card">
        <td>{{ row.card }}</td>
        <td>{{ row.name }}</td>
        <td>{{ row.closeTime || "-" }}</td>
        <td>
          <span class="tag" :class="statusView(row.status).cls">{{ statusView(row.status).text }}</span>
        </td>
        <td class="right">
          <!-- 正常账户：可锁定 -->
          <button
              v-if="row.status === 'ACTIVE' || row.status === 'TEST'"
              class="btn-op btn-lock"
              :disabled="busy === row.card"
              @click="doAction(row, 'lock')"
          >锁定</button>
          <!-- 已锁定账户：可解锁 -->
          <button
              v-if="row.status === 'LOCKED'"
              class="btn-op btn-unlock"
              :disabled="busy === row.card"
              @click="doAction(row, 'unlock')"
          >解锁</button>
          <!-- 已注销账户：可取消注销 -->
          <button
              v-if="row.status === 'CANCEL'"
              class="btn-op btn-cancel"
              :disabled="busy === row.card"
              @click="doAction(row, 'cancel')"
          >取消注销</button>
        </td>
      </tr>
      <tr v-if="!loading && accounts.length === 0">
        <td colspan="5" class="empty">暂无账户</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 22px;
  font-weight: 600;
  color: #0a4fa3;
  margin-bottom: 28px;
}
.panel {
  background: #fff;
  border: 1px solid #e5eaf2;
  border-radius: 14px;
  padding: 10px 30px;
  min-height: 120px;
}
.tbl {
  width: 100%;
  border-collapse: collapse;
}
.tbl th {
  text-align: left;
  font-size: 12px;
  font-weight: 400;
  color: #8a94a6;
  padding: 26px 0 12px;
  border-bottom: 1px solid #e5eaf2;
}
.tbl td {
  font-size: 14px;
  color: #1f2d3d;
  height: 52px;
  border-bottom: 1px solid #e5eaf2;
}
.tbl tr:last-child td {
  border-bottom: none;
}
.right {
  text-align: right !important;
}
.tag {
  display: inline-block;
  padding: 0 12px;
  height: 24px;
  line-height: 24px;
  border-radius: 6px;
  font-size: 12px;
}
.tag.ok { background: #e6f7ec; color: #14783a; }
.tag.cancel { background: #eef0f4; color: #6b7280; }
.tag.locked { background: #fff4db; color: #8a5a00; }

.btn-op {
  min-width: 60px;
  height: 28px;
  padding: 0 12px;
  border-radius: 6px;
  font-size: 13px;
  cursor: pointer;
  margin-left: 8px;
}
.btn-op:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.btn-unlock,
.btn-cancel {
  border: none;
  background: #0a66d9;
  color: #fff;
}
.btn-lock {
  border: 1px solid #d93025;
  background: #fff;
  color: #d93025;
}
.empty {
  text-align: center !important;
  color: #8a94a6 !important;
}
</style>