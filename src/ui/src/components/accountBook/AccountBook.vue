<template>
  <div class="book_all_container">
    <div class="table_container">
      <div class="button_container">
                <button @click="openModal">등록</button>
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

    <Modal v-if="showModal" @close-modal="showModal = false" >
      <AddData @success="addSuccess"/>
    </Modal>
  </div>


</template>

<script>


import DataTable from "@/components/accountBook/AccountBookDataTable";
import Modal from "@/components/Modal";
import AddData from "@/components/accountBook/AddData";
import {findBookByName} from "@/api/AccountBookApi";
import DataSearchBar from "@/components/accountBook/DataSearchBar";
import SimpleStatistic from "@/components/statistic/SimpleStatistic";

export default {
  name: "AccountBook",
  components: {
    SimpleStatistic,
    DataSearchBar,
    DataTable,
    Modal,
    AddData
  },
  data(){
    return {
      showModal: false,
      showStatistic : false,
      bookName: '집',
      totalAmount: '0'
    }
  },

  methods:{
    openModal(evt){
      evt.preventDefault()
      this.showModal = true
      this.$refs.dataTable.search();
    },

    addSuccess(){
      this.showModal = false;
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
      console.log(data)
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