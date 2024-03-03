<template>
<div>
  <button @click="btnClick"> 계산!! </button>
  <div id="cal-data-container">
    <CalculateData v-for="calculateData in calculatedDataList" v-bind:key="calculateData.period" :calculated-data="calculateData" ref="calData"/>
  </div>
</div>
</template>

<script>
import CalculateData from "@/components/calculate/CalculateData";
import {findMonthlyCalculated} from "@/api/CalculateApi";
import {setComma} from "@/common/CommonJs";

export default {
  name: "CalculateMain",
  components : {
    CalculateData
  },

  data() {
    return {
      calculatedDataList: []
    }
  },

  mounted() {
    findMonthlyCalculated().then(data => {
      this.calculatedDataList = data.body;
    })

    console.log(this.$refs.calData)
  },

  methods: {
    btnClick() {
      const calDatas = this.$refs.calData;

      let totalAmount = 0;
      let avgAmount = 0;
      calDatas.forEach((calComp, idx) => {
        const amount = Number(calComp.getSumAmount().replaceAll(",",""))
        totalAmount += amount
      })

      avgAmount = totalAmount / 12;

      alert('총 금액 : ' + setComma(totalAmount) + '\n' + '평균 : ' + setComma(avgAmount))
    }
  }

}
</script>

<style scoped>
#cal-data-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>