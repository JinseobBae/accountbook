<template>
  <Pie
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :dataset-id-key="datasetIdKey"
      :css-classes="cssClasses"
      :styles="styles"
      :width="width"
      :height="height"
  />


</template>

<script>
import { Pie } from 'vue-chartjs/legacy'

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale
} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)

export default {
  name: "PieChart",
  components: {
    Pie
  },
  props: {
    chartId: {
      type: String,
      default: 'pie-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
    width: {
      type: Number,
      default: 300
    },
    height: {
      type: Number,
      default: 300
    },
    cssClasses: {
      default: '',
      type: String
    },
    styles: {
      type: Object,
      default: () => { return {
        width: '20vw',
        height: `300px`,
        position: 'relative'
      }}
    },
    chartData: {
      type: Object,
      required: true
    },
    title: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: this.title !== "",
            text: this.title
          }
        }
      }
    }
  },

  // computed: {
  //   myStyles(){
  //     return {
  //       width: '400px',
  //       height: `300px`,
  //       position: 'relative'
  //     }
  //   }
  // },


}
</script>

<style scoped>

</style>