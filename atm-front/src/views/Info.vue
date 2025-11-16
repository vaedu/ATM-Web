<template>
  <div class="container">
    <NavBar/>
    <div class="card" style="max-width:720px;margin:10px auto">
      <div class="h1">账户信息</div>
      <div style="display:flex;gap:18px;flex-wrap:wrap;margin-top:12px">
        <div style="flex:1;min-width:220px">
          <div class="h2">姓名</div>
          <div style="margin-top:6px">{{ acc?.name || '' }}</div>
        </div>
        <div style="flex:1;min-width:160px">
          <div class="h2">性别</div>
          <div style="margin-top:6px">{{ acc.sex || '' }}</div>
        </div>
        <div style="flex:1;min-width:240px">
          <div class="h2">卡号</div>
          <div style="margin-top:6px;display:flex;align-items:center;gap:8px">
            <div>{{ masked }}</div>
            <img :src="eyeIcon" @click="onShow" style="width:20px;cursor:pointer" :title="showAll ? '隐藏卡号' : '查看全部卡号'"/>
          </div>
        </div>
      </div>

      <div style="margin-top:16px;display:flex;gap:12px;flex-wrap:wrap">
        <div style="flex:1">
          <div class="h2">余额</div>
          <div style="margin-top:6px;font-weight:800">{{ acc ? Number(acc.balance).toFixed(2) : '0.00' }} 元</div>
        </div>
        <div style="flex:1">
          <div class="h2">本日限额</div>
          <div style="margin-top:6px">{{ acc ? Number(acc.limit).toFixed(2) : '0.00' }} 元</div>
        </div>
      </div>

      <div style="margin-top:18px;text-align:right">
        <button class="btn ghost" @click="$router.push('/home')">返回首页</button>
      </div>
    </div>

    <!-- 密码弹窗 -->
    <div v-if="showModal" class="modal-veil">
      <div class="card" style="max-width:360px;margin:0 auto">
        <div class="h2">输入密码查看完整卡号</div>
        <input class="input" v-model="pwd" type="password" placeholder="请输入密码"/>
        <div style="height:12px"></div>
        <div style="display:flex;justify-content:flex-end;gap:8px">
          <button class="btn secondary" @click="showModal=false">取消</button>
          <button class="btn" @click="verify">确认</button>
        </div>
        <p v-if="err" style="color:var(--danger);margin-top:8px">{{err}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import displayIcon from '@/assets/icon/display.svg';
import noDisplayIcon from '@/assets/icon/no-display.svg';
export default {
  components:{NavBar},
  data(){ return { acc:null, showModal:false, pwd:'', err:'', showAll:false, eyeIcon:displayIcon } },
  created(){
    const s = localStorage.getItem('account');
    this.acc = s ? JSON.parse(s) : null;
    this.updateMasked();
  },
  computed:{
    masked(){ if(!this.acc?.card) return ''; const c=String(this.acc.card); return c.slice(0,4)+' **** '+c.slice(-4); }
  },
  methods:{
    onShow(){ this.showModal=true; this.err=''; this.pwd=''; },
    async verify(){
      if(!this.pwd){ this.err='请输入密码'; return; }
      if(this.pwd===this.acc.password){
        this.showAll=true;
        this.showModal=false;
        this.eyeIcon = noDisplayIcon;
        // 直接替换显示全部
        alert('完整卡号：' + this.acc.card);
      } else this.err='密码错误';
    },
    updateMasked(){ /* computed handles it */ }
  }
}
</script>

<style scoped>
.modal-veil{ position:fixed; inset:0; background:rgba(2,6,23,0.45); display:flex; align-items:center; justify-content:center; padding:20px }
</style>
