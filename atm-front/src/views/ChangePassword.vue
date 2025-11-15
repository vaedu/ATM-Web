<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:520px;margin:20px auto">
      <div class="h1">更改密码</div>
      <input class="input" v-model="currentPassword" type="password" placeholder="当前密码" />
      <input class="input" v-model="newPassword" type="password" placeholder="新密码（6位数字）" />
      <input class="input" v-model="confirmPassword" type="password" placeholder="确认新密码" />
      <div style="height:12px"></div>
      <button class="btn" @click="updatePassword">确认修改</button>
      <p v-if="msg" :style="{color: msgErr? 'var(--danger)':'var(--success)'}">{{msg}}</p>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
export default {
  components:{NavBar},
  data(){ return { currentPassword: '', newPassword: '', confirmPassword: '', msg: '', msgErr: false, user: JSON.parse(localStorage.getItem('account')) } },
  methods:{
    async updatePassword(){
      this.msg = '';
      this.msgErr = false;
      if (!this.currentPassword || !this.newPassword || !this.confirmPassword) {
        this.msg = '请填写完整信息';
        this.msgErr = true;
        return;
      }
      if (this.newPassword !== this.confirmPassword) {
        this.msg = '新密码与确认密码不一致';
        this.msgErr = true;
        return;
      }
      if (!/^\d{6}$/.test(this.newPassword)) {
        this.msg = '新密码必须为6位数字';
        this.msgErr = true;
        return;
      }

      try {
        const res = await axios.post('http://localhost:8090/api/atm/change-password', null, { params: {
            card: this.user.card,
            currentPassword: this.currentPassword,
            newPassword: this.newPassword
          }});

        if (res.data === true) {
          this.msg = '密码修改成功';
          this.user.password = this.newPassword;
          localStorage.setItem('account', JSON.stringify(this.user));
          setTimeout(() => {
            this.$router.push('/home');
          }, 1000);
        } else {
          this.msg = '当前密码错误';
          this.msgErr = true;
        }
      } catch (e) {
        this.msg = '服务器连接失败';
        this.msgErr = true;
      }
    }
  }
}
</script>

<style scoped>
.card {
  padding: 22px;
  max-width: 500px;
}
</style>
