<template>
<div>
  <BarChart :chart-data="lastMonthCompareData"/>
</div>
</template>

<script>
import BarChart from "@/components/chart/BarChart";
import { getLastMonthCompare } from "@/api/TotalStatisticApi"

export default {
  name: "TotalStatistic",
  components: {BarChart},
  data() {
    return{
      lastMonthCompareData: {}
    }
  },
  mounted() {
    getLastMonthCompare({accountBookName : "집"}).then((response) => {
      const data = response.body;

      this.lastMonthCompareData = this.generateBarCharData(data)

    })
  },

  methods: {
    generateBarCharData(data){

      console.log(data)

      const labels = []
      const chartData = []
      let dataLabel = ''



      data.forEach( (d) => {
        labels.push(d.label)
        chartData.push(d.totalAmount)

        if(dataLabel === ''){
          if(d.type === null){
            dataLabel = '사용금액'
          }else{
            dataLabel = d.type
          }
        }
      })

      return {
        labels: labels,
        datasets: [
          {
            label: dataLabel,
            backgroundColor: ['#41B883'],
            data: chartData
          },
        ]
      }
    }
  }
}
</script>

<style scoped>

</style>