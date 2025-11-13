<template>
  <div class="info-container">
    <h2>账户信息</h2>
    <p><strong>姓名：</strong>{{ account.name }}</p>
    <p><strong>性别：</strong>{{ account.sex }}</p>
    <p><strong>卡号：</strong>
      <span v-if="!showCardNumber">{{ maskedCardNumber }}</span>
      <span v-else>{{ fullCardNumber }}</span>
      <button @click="toggleDisplay">
        <img :src="eyeIcon" class="eye-icon"  alt="查看完整卡号"/>
      </button>
    </p>
    <p><strong>余额：</strong>{{ account.balance }}</p>
    <p><strong>本日限额：</strong>{{ account.limit }}</p>

    <!-- 查看完整卡号弹窗 -->
    <div v-if="showCardNumberModal" class="modal">
      <div class="modal-content">
        <button class="close-button" @click="closeModal">&times;</button>
        <p><strong>请输入密码查看完整卡号：</strong></p>
        <input type="password" v-model="password" placeholder="请输入密码" @input="clearPasswordError" />
        <button @click="verifyPassword">确认</button>
        <p v-if="passwordError" class="error-message">密码错误，请重新输入</p>
      </div>
    </div>

    <!-- 返回首页按钮 -->
    <button @click="goHome">返回首页</button>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router'; // 导入 useRouter 以支持路由跳转
import displayIcon from '../assets/icon/display.svg';
import noDisplayIcon from '../assets/icon/no-display.svg';

const router = useRouter(); // 使用 composition API 的 router

// 用户账户信息
const account = ref({});
const showCardNumber = ref(false); // 控制卡号显示
const showCardNumberModal = ref(false); // 弹窗显示
const password = ref(''); // 密码输入框
const passwordError = ref(false); // 密码错误标志
const eyeIcon = ref(noDisplayIcon); // 默认隐藏图标

// 获取本地存储的账户信息
onMounted(() => {
  const stored = localStorage.getItem('account');
  if (stored) {
    const parsed = JSON.parse(stored);
    account.value = parsed.data ? parsed.data : parsed;
  }
});

// 切换卡号显示
function toggleDisplay() {
  if (showCardNumber.value) {
    // 如果当前显示完整，切换到隐藏
    showCardNumber.value = false;
    eyeIcon.value = noDisplayIcon;
    clearPassword(); // 隐藏时清空密码
  } else {
    // 如果当前隐藏，弹出密码验证
    showCardNumberModal.value = true;
    passwordError.value = false; // 重置错误状态
  }
}

// 验证密码是否正确
function verifyPassword() {
  if (password.value === account.value.password) {
    showCardNumberModal.value = false;
    showCardNumber.value = true;
    eyeIcon.value = displayIcon;
    clearPassword(); // 验证成功后清空密码
  } else {
    passwordError.value = true;
  }
}

// 清空密码输入
function clearPassword() {
  password.value = '';
}

// 清空密码错误状态（输入时触发）
function clearPasswordError() {
  passwordError.value = false;
}

// 关闭弹窗
function closeModal() {
  showCardNumberModal.value = false;
  clearPassword();
  passwordError.value = false;
}

// 获取遮掩后的卡号
const maskedCardNumber = computed(() => {
  const card = account.value.card || '';
  return `${card.slice(0, 4)}**** **** ${card.slice(-4)}`;
});

// 获取完整卡号
const fullCardNumber = computed(() => {
  return account.value.card || '';
});

// 返回首页功能
function goHome() {
  router.push('/home');
}
</script>

<style scoped>
.info-container {
  padding: 20px;
}

h2 {
  margin-bottom: 10px;
}

button {
  margin-top: 10px;
}

.error-message {
  color: red;
  font-size: 12px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
  text-align: center;
  position: relative; /* 为关闭按钮定位 */
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #999;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-button:hover {
  color: #000;
}

.eye-icon {
  width: 20px;
  cursor: pointer;
}
</style>