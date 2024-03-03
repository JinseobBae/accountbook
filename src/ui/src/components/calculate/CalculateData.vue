<template>
  <div>

    <div>
      <table style="border-bottom: #000088 solid 1px;">
        <tbody class="calculate-body">
        <tr>
          <td class="period-td" rowspan="2">{{ period }}</td>
          <td class="inTotal-td">{{ inTotal }}</td>
          <td class="outTotal-td">{{ outTotal }}</td>
          <td class="sum-td" rowspan="2" v-html="sumAmount"></td>
        </tr>
        <tr>
          <td class="inDetail-td" v-html="inDetails"></td>
          <td class="outDetail-td" v-html="outDetails"></td>
        </tr>
        </tbody>
      </table>

    </div>

  </div>
</template>

<script>
import {setComma} from "@/common/CommonJs";

export default {
  name: "CalculateData",
  data() {
    return {
      period: "",
      inTotal: 0,
      outTotal: 0,
      inDetail: "",
      outDetail: "",
      sumAmount: 0,
    }
  },
  props: {
    calculatedData: {
      type: Object
    }
  },

  mounted() {
    const year = this.calculatedData.period.substr(0, 4);
    const month = this.calculatedData.period.substr(4, 2);
    this.period = year + '년 ' + month + '월'
    this.inTotal = setComma(this.calculatedData.inTotal)
    this.outTotal = setComma(this.calculatedData.outTotal)

    this.calculatedData.inList.forEach((inData) => {
      this.inDetail += inData.categoryName
      this.inDetail += " : "
      this.inDetail += setComma(inData.totalAmount)
      this.inDetail += "\n"
    })

    this.outDetail += '<table>'

    this.calculatedData.outList.forEach((outData, idx) => {
      if (idx % 3 === 0) {
        this.outDetail += '<tr>'
      }

      this.outDetail += '<td style="float: left; margin-right: 10px; ">'
      this.outDetail += outData.categoryName
      this.outDetail += ' : '
      this.outDetail += setComma(outData.totalAmount)
      this.outDetail += '</td>'

      if (idx % 3 === 2) {
        this.outDetail += '</tr>'
      }
    })

    const sum =  this.calculatedData.inTotal - this.calculatedData.outTotal;

    this.sumAmount = setComma(sum)
  },

  computed: {
    outDetails() {
      return this.outDetail.replaceAll("\n", "<br />")
    },

    inDetails() {
      return this.inDetail.replaceAll("\n", "<br />")
    }

  },

  methods: {
    getSumAmount () {
      return this.sumAmount;
    }
  }
}
</script>

<style scoped>

.period-td {
  width: 10vw;
}

.inTotal-td {
  width: 10vw;
  height: 7vh;
  font-size: 25px;
  color: #009926;
}

.outTotal-td {
  width: 30vw;
  font-size: 25px;
  color: red;
}

.calculate-body {
  margin-top: 10px;
  margin-bottom: 10px;
  height: 10vh;
}

.outDetail-td {
  display: flex;
  justify-content: center;
}

.sum-td {
  font-size: 20px;
}

</style>