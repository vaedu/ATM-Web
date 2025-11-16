<template>
  <div class="container">
    <NavBar/>

    <div class="card">
      <h2>自助开户</h2>

      <label>姓名</label>
      <input class="input" v-model="name"/>

      <label>密码（6位数字）</label>
      <input class="input" v-model="password" type="password"/>

      <label>性别</label>
      <select class="input" v-model="sex">
        <option value="男">男</option>
        <option value="女">女</option>
      </select>

      <label>每日限额</label>
      <input class="input" v-model="limit"/>

      <button class="btn" @click="doReg">确认开户</button>

      <p v-if="msg">{{msg}}</p>

      <button class="btn secondary" @click="$router.push('/')">返回登录</button>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import axios from 'axios'

export default {
  components:{NavBar},
  data(){
    return{
      name:'', password:'', sex:'男', limit:'', msg:''
    }
  },
  methods:{
    async doReg(){
      this.msg=''
      try{
        const res = await axios.post("http://localhost:8090/api/atm/register", {
          name:this.name,
          password:this.password,
          sex:this.sex,
          limit:Number(this.limit)
        })
        if(res.data){
          this.msg="开户成功！您的卡号为：" + res.data.card
        }
      }catch(e){
        this.msg="服务器错误"
      }
    }
  }
}
</script>
