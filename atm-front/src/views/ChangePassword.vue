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
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';

export default {
  components:{NavBar},
  data(){
    return{
      oldPwd:'', newPwd:'', msg:''
    }
  },
  methods:{
    async doChange(){
      this.msg=''
      const acc = JSON.parse(localStorage.getItem("account"))

      const res = await axios.post("http://localhost:8090/api/atm/change-password", {
        card: acc.card,
        oldPwd: this.oldPwd,
        newPwd: this.newPwd
      })

      if(res.data){
        this.msg="修改成功！"
      }else{
        this.msg="旧密码不正确"
      }
    }
  }
}
</script>
