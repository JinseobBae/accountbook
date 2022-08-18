<template>
<div id="total-chart-container">
  <div id="month-compare-chart-container">
    <span>
      <BarChart chart-id="mb" :chart-data="lastMonthCompareData" title="최근 6개월간 사용 총액"/>
    </span>
    <span>
      <BarChart chart-id="mdb" :stack=true :chart-data="lastMonthCompareDataDetail" title="최근 6개월간 항목별 사용 총액"/>
    </span>
  </div>
  <div>
    asdfasdf
  </div>
  <div>
    asdfasdf
  </div>
</div>
</template>

<script>
import BarChart from "@/components/chart/BarChart";
import { getLastMonthCompare, getLastMonthCompareDetail } from "@/api/TotalStatisticApi"
import { genMonthlyBarCharData, genMonthlyBarCharDataDetail } from "@/script/TotalStatisticScript"

export default {
  name: "TotalStatistic",
  components: {BarChart},
  data() {
    return{
      lastMonthCompareData: {},
      lastMonthCompareDataDetail: {}
    }
  },
  mounted() {
    const searchForm = { accountBookName : "집" }

    getLastMonthCompare(searchForm).then((response) => {
      const data = response.body;
      this.lastMonthCompareData = genMonthlyBarCharData(data)
    })

    getLastMonthCompareDetail(searchForm).then((response) => {
      const data = response.body;
      this.lastMonthCompareDataDetail = genMonthlyBarCharDataDetail(data)
    })
  },
}
</script>

<style scoped>

#total-chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#month-compare-chart-container {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-evenly;
}


</style>