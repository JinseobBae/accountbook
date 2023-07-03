<template>
<div id="total-chart-container">
  <div id="month-compare-chart-container">
    <div>
      <BarChart chart-id="mb" :chart-data="lastMonthCompareData" :styles=chartStyle title="최근 6개월간 사용 총액"/>
    </div>
    <div class="chart-border"/>
    <div>
      <BarChart chart-id="mdb" :stack=true :chart-data="lastMonthCompareDataDetail" :styles=chartStyle title="최근 6개월간 항목별 사용 총액"/>
    </div>
  </div>
  <div id="month-compare-chart-container2">
    <div>
      <BarChart chart-id="mdb2" :stack=true :chart-data="lastMonthCompareDataLifeDetail" :styles=chartStyle title="최근 6개월간 생활 총액"/>
    </div>
    <div class="chart-border"/>
    <div>
      <BarChart chart-id="mbd3" :stack=true :chart-data="lastMonthCompareDataTaxDetail" :styles=chartStyle title="최근 6개월간 항목별 공과금 총액"/>
    </div>
  </div>
</div>
</template>

<script>
import BarChart from "@/components/chart/BarChart";
import { getLastMonthCompare, getLastMonthCompareDetail, getLastMonthCompareLifeDetail, getLastMonthCompareTaxDetail } from "@/api/TotalStatisticApi"
import { genMonthlyBarCharData, genMonthlyBarCharDataDetail } from "@/script/TotalStatisticScript"

export default {
  name: "TotalStatistic",
  components: {BarChart},
  data() {
    return{
      lastMonthCompareData: {},
      lastMonthCompareDataDetail: {},
      lastMonthCompareDataLifeDetail: {},
      lastMonthCompareDataTaxDetail: {},
      chartStyle: {}
    }
  },

  created() {
    if(this.isMobileView()){
      this.chartStyle = {
        width: '90vw',
        height: '55vh',
        position: 'relative'
      }
    }else{
      this.chartStyle = {
        width: '50vw',
        height: '55vh',
        position: 'relative'
      }
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
      this.lastMonthCompareDataDetail = genMonthlyBarCharDataDetail(data, 'category1')
    })

    getLastMonthCompareLifeDetail(searchForm).then((response) => {
      const data = response.body;
      this.lastMonthCompareDataLifeDetail = genMonthlyBarCharDataDetail(data, 'category2')
    })

    getLastMonthCompareTaxDetail(searchForm).then((response) => {
      const data = response.body;
      this.lastMonthCompareDataTaxDetail = genMonthlyBarCharDataDetail(data, 'category2')
    })
  },

  methods : {
    isMobileView() {
      if (screen.width <= 768) {
        return true
      } else {
        return false
      }
    },
  }
}
</script>

<style scoped>

#total-chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#month-compare-chart-container, #month-compare-chart-container2 {
  display: flex;
  flex-direction: row;
  width: 98%;
  justify-content: space-evenly;
}

@media (max-width: 768px)  {
  #month-compare-chart-container, #month-compare-chart-container2 {
    display: flex;
    width: 98%;
    flex-direction: column;
    align-items: center;
  }

  .chart-border {
    margin: 5px;
  }
}

</style>