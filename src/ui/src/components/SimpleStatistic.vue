<template>
  <div id="simple_stat_container">
    총 금액 :  {{statisticData.totalAmount}}
  </div>
</template>

<script>
import { getContentStatistic } from "@/api/ContentStatisticApi"
import { setComma } from "@/common/CommonJs"

export default {
  //https://vue-chartjs.org/guide/#introduction
  name: "SimpleStatistic",
  data(){
    return {
      statisticData: {}
    }
  },
  methods: {
    doStatistic(formData){
      getContentStatistic(formData).then(response => {
        this.statisticData = response.body;
        this.statisticData.totalAmount = setComma(this.statisticData.totalAmount)
      })
    },
  }
}
</script>

<style scoped>
  #simple_stat_container {
    height: 500px;
  }
</style>