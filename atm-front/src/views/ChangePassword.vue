<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>修改密码</h2>

      <input class="input" v-model="oldPwd" placeholder="旧密码" type="password"/>
      <input class="input" v-model="newPwd" placeholder="新密码" type="password"/>

      <button class="btn" @click="doChange">确认修改</button>

      <p v-if="msg" style="margin-top:10px">{{msg}}</p>

      <button class="btn secondary" @click="$router.push('/home')">返回</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import NavBar from "@/components/NavBar.vue";

export default {
  components: {NavBar},
  data() {
    return {
      oldPwd: "",
      newPwd: "",
      msg: ""
    };
  },
  methods: {
    async doChange() {

      const acc = JSON.parse(localStorage.getItem("account")); // ← 正确的变量

      if (!acc) {
        this.msg = "请重新登录";
        return;
      }

      try {
        const resp = await axios.post("http://localhost:8090/api/atm/change", {
          card: acc.card,
          oldPwd: this.oldPwd,
          newPwd: this.newPwd
        });

        if (resp.data.success) {
          this.msg = "密码修改成功";
        } else {
          this.msg = resp.data.message;
        }
      } catch (e) {
        this.msg = "服务器错误";
      }
    }
  }
};
</script>

