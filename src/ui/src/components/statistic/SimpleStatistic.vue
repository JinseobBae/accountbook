<template>
  <div id="simple_stat_container">
    <div id="total_amount_statistic">
      총 금액 :  {{totalAmount}}
    </div>
    <div class="pie_chart_container">
      <span>
        <PieChart chart-id="c1" :chart-data="typeStatisticData" v-show="showTypeChart"/>
      </span>
      <span>
        <PieChart chart-id="c2" :chart-data="category1StatisticData" v-show="showCategory1Chart"/>
      </span>
      <span>
        <PieChart chart-id="c3" :chart-data="category2StatisticData"/>
      </span>
    </div>
  </div>
</template>

<script>
import { getContentStatistic } from "@/api/ContentStatisticApi"
import { setComma } from "@/common/CommonJs"
import PieChart from "@/components/chart/PieChart";

export default {
  //https://vue-chartjs.org/guide/#introduction
  name: "SimpleStatistic",
  components: {PieChart},
  data(){
    return {
      totalAmount: 0,
      typeStatisticData: {},
      category1StatisticData: {},
      category2StatisticData: {},
      showTypeChart: true,
      showCategory1Chart: true,
    }
  },
  methods: {
    doStatistic(formData){
      getContentStatistic(formData).then(response => {
        const statisticData = response.body;

        this.totalAmount = setComma(statisticData.totalAmount[0].totalAmount)
        this.typeStatisticData = this.generateCharData(statisticData.typeStatistic)
        this.category1StatisticData = this.generateCharData(statisticData.category1Statistic)
        this.category2StatisticData = this.generateCharData(statisticData.category2Statistic)

        console.log(formData.type)
        console.log(formData.category1)
        if (formData.category1 !== undefined){
          this.showTypeChart = false
          this.showCategory1Chart = false
        }else if(formData.type !== undefined){
          this.showTypeChart = false
        }
      })
    },

    generateCharData(data){

      const labels = []
      const chartData = []

      data.forEach((d) => {
        if(d.category2 !== null){
          labels.push(d.category2)
        }else if(d.category1 !== null){
          labels.push(d.category1)
        }else{
          labels.push(d.type)
        }

        chartData.push(d.totalAmount)
      })

      return {
        labels: labels,
        datasets: [
          {
            backgroundColor: [
                '#41B883', '#E46651', '#00D8FF', '#DD1B16'
              , '#7442e5', '#b4a9a9', '#debf76', '#16dd70'
              , '#2a6dde', '#e7791d'],
            data: chartData
          }
        ]
      }
    }
  }
}
</script>

<style scoped>
  #simple_stat_container {
    height: 500px;
  }

  .pie_chart_container {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  #total_amount_statistic {
    font-size: 3vw;
  }

  @media (max-width: 768px) {
    .pie_chart_container {
      display: flex;
      flex-direction: column;
    }
  }

</style>