<template>
  <div class="card">
    <div class="h2">最近交易</div>
    <div v-if="list.length===0" class="muted">暂无交易记录</div>
    <ul class="tx-list">
      <li v-for="(t, i) in list" :key="i" class="tx-item">
        <div class="tx-left">
          <div class="tx-type">{{ t.type }}</div>
          <div class="tx-time">{{ formatTime(t.time) }}</div>
        </div>
        <div class="tx-right" :class="amountClass(t.type)">
          <div class="tx-amount">{{ formatAmount(t.amount) }}</div>
          <div class="tx-remark">{{ t.remark || '' }}</div>
        </div>
      </li>
    </ul>
    <div class="tx-foot">
      <button class="btn secondary" @click="$emit('refresh')">刷新</button>
    </div>
  </div>
</template>

<script>
export default {
  name:'TransactionList',
  props:{ list:{ type:Array, default:()=>[] } },
  methods:{
    formatTime(s){ if(!s) return ''; return new Date(s).toLocaleString(); },
    formatAmount(a){ return Number(a).toFixed(2) + ' 元'; },
    amountClass(type){
      if(type==='存款' || type==='收款') return 'inc';
      if(type==='取款' || type==='转账') return 'dec';
      return '';
    }
  }
}
</script>

<style scoped>
.tx-list{list-style:none;margin-top:12px}
.tx-item{display:flex;justify-content:space-between;padding:12px 6px;border-bottom:1px dashed #eef3ff}
.tx-left{display:flex;flex-direction:column}
.tx-type{font-weight:700;color:var(--bank-deep)}
.tx-time{font-size:12px;color:var(--muted)}
.tx-right{text-align:right}
.tx-amount{font-weight:800}
.tx-right.inc .tx-amount{color:var(--success)}
.tx-right.dec .tx-amount{color:var(--danger)}
.tx-foot{margin-top:12px;text-align:right}
.muted{color:var(--muted);padding:12px 0}
</style>
