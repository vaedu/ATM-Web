<template>
  <div>
    <div class="tx" v-for="(item,i) in list" :key="i">
      <div>{{ mapType(item.type) }}</div>
      <div>{{ item.amount }} 元</div>
      <div class="time">{{ item.time }}</div>
      <div>{{ item.remark }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props:["list"],
  methods:{
    mapType(t){
      switch(t){
        case "DEPOSIT": return "存款"
        case "WITHDRAW": return "取款"
        case "TRANSFER_OUT": return "转账支出"
        case "TRANSFER_IN": return "转账收入"
        default: return t
      }
    }
  }
}
</script>

<style scoped>
.tx{
  padding:10px;
  border-bottom:1px solid #ddd;
}
.time{
  font-size:12px;
  color:gray;
}
</style>
