<template>
  <div class="book_all_container">
    <div style="float:left;">
      <button @click="hello" style="width: 10px; font-size: 10px; background: white; cursor: inherit">a</button>
    </div>
    <div class="table_container">
      <div class="button_container">
        <button @click="openInModal">입금</button>
        <button @click="openOutModal">출금</button>
      </div>

      <div class="search_bar_container">
        <DataSearchBar @search="searchData" @clean="cleanSearch"/>
      </div>

      <div id="dataTable">
        <DataTable :book-name="bookName" ref="dataTable" />
      </div>

      <div class="total_amount_container">
        현재 잔고 : {{totalAmount}}
      </div>

    </div>

    <div>
      <SimpleStatistic v-show="showStatistic" ref="statistic"/>
    </div>

    <Modal v-if="showInModal" @close-modal="showInModal = false" >
      <AddInData @success="addSuccess"/>
    </Modal>
    <Modal v-if="showOutModal" @close-modal="showOutModal = false" >
      <AddOutData @success="addSuccess"/>
    </Modal>
  </div>


</template>

<script>


import DataTable from "@/components/accountBook/AccountBookDataTable";
import Modal from "@/components/Modal";
import {findBookByName, doAuth} from "@/api/AccountBookApi";
import DataSearchBar from "@/components/accountBook/DataSearchBar";
import SimpleStatistic from "@/components/statistic/SimpleStatistic";
import AddInData from "@/components/accountBook/AddInData";
import AddOutData from "@/components/accountBook/AddOutData";

export default {
  name: "AccountBook",
  components: {
    SimpleStatistic,
    DataSearchBar,
    DataTable,
    Modal,
    AddOutData,
    AddInData
  },
  data(){
    return {
      showModal: false,
      showInModal: false,
      showOutModal: false,
      showStatistic : false,
      bookName: '집',
      totalAmount: '0'
    }
  },

  methods:{
    openInModal(evt){
      evt.preventDefault()
      this.showInModal = true
      this.$refs.dataTable.search();
    },

    openOutModal(evt){
      evt.preventDefault()
      this.showOutModal = true
      this.$refs.dataTable.search();
    },

    hello() {
      const answer = prompt("hello?")
      doAuth(answer);
    },

    addSuccess(){
      this.showInModal = false;
      this.showOutModal = false;
      window.location.reload();
    },

    searchData(formData){
      this.showStatistic = true
      this.$refs.dataTable.search(formData)
      this.$refs.statistic.doStatistic(formData)
    },

    cleanSearch(){
      this.showStatistic = false
    }
  },

  mounted() {
    findBookByName(this.bookName).then( data => {
      this.totalAmount = data.body.totalAmount.toLocaleString("ko-KR")
    })
  }
}
</script>
<style scoped>

.table_container{
  display: flex;
  align-items: center;
  flex-direction: column;
}

.button_container{
  margin-bottom: 3vh;
}

.search_bar_container{
  display: flex;
  align-items: center;
  flex-direction: column;

  width: 80vw;
  margin-bottom: 3vh;
}

#dataTable{
  display: inline-block;
  width: 80vw;
}

button {
  width: 150px;
  padding: 10px;
  border: none;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
  background-color: #007AFF;
  font-size: 16px;
  color: #fff;
  cursor: pointer;
  margin-right: 10px;
}
button:hover {
  background-color: #0666a3;
}

.total_amount_container{
  margin-top: 10vh;
  margin-bottom: 3vh;
  font-size: 5vmin;
}

</style>