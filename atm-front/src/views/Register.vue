<template>
  <div class="register-page">

    <NavBar />

    <div class="register-card">
      <h2 class="title">开户申请</h2>

      <div class="input-group">
        <label>姓名</label>
        <input v-model="name" class="input" placeholder="请输入姓名" />
      </div>

      <div class="input-group">
        <label>卡号</label>
        <input v-model="card" class="input" placeholder="请输入卡号（8~20位）" />
      </div>

      <div class="input-group">
        <label>密码</label>
        <input type="password" v-model="password" class="input" placeholder="请输入密码" />
      </div>

      <div class="input-group">
        <label>确认密码</label>
        <input type="password" v-model="confirm" class="input" placeholder="再次输入密码" />
      </div>

      <div class="input-group">
        <label>性别</label>
        <select v-model="sex" class="input">
          <option value="">请选择</option>
          <option value="男">男</option>
          <option value="女">女</option>
        </select>
      </div>

      <button class="btn primary" @click="submitRegister">提交开户申请</button>

      <p v-if="msg" class="msg">{{ msg }}</p>

      <button class="btn secondary" @click="$router.push('/login')">返回登录</button>
    </div>
  </div>
</template>

<script src="@/assets/scripts/register.js"></script>
<style src="@/assets/styles/register.css"></style>
