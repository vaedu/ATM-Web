<template>
  <div class="page">
    <NavBar />
    <h2>修改密码</h2>
    <input type="password" v-model="oldPwd" placeholder="原密码" />
    <input type="password" v-model="newPwd" placeholder="新密码" />
    <input type="password" v-model="confirmPwd" placeholder="确认新密码" />
    <button @click="changePassword">确认修改</button>
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
      oldPwd: "",
      newPwd: "",
      confirmPwd: ""
    };
  },
  methods: {
    async changePassword() {
      if (this.newPwd !== this.confirmPwd) return alert("两次输入不一致！");
      try {
        const res = await axios.post("http://localhost:8090/api/atm/changePassword", null, {
          params: {
            card: this.user.card,
            oldPwd: this.oldPwd,
            newPwd: this.newPwd
          }
        });
        alert(res.data.message || "密码修改成功！");
      } catch {
        alert("修改失败！");
      }
    }
  }
};
</script>
