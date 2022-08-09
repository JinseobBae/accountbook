<template>
  <div class="table_data_container">
    <ve-table :columns="columns" :table-data="tableData" max-height="46vh" />
    <div v-show="showEmpty" class="empty-data">조회 결과가 없습니다.</div>
    <div class="table-pagination">
      <ve-pagination :total="total"
                     :page-index="pageIndex"
                     :page-size="pageSize"
                     @on-page-number-change="pageNumberChange"
                     @on-page-size-change="pageSizeChange"/>
    </div>

  </div>
</template>

<script>
import AccountBookLayout from "@/layout/AccountBookLayout";
import {findContent} from "@/api/AccountBookContentApi";

let DATA = [];

export default {
  //https://happy-coding-clans.github.io/vue-easytable/#/en/doc/intro
  name: "DataTable",
  data() {
    return {
      columns: AccountBookLayout,
      tableData: [],
      total: 0,
      pageIndex: 1,
      pageSize: 10,
      showEmpty: false
    };
  },
  props: {
    bookName: {
      type : String
    }
  },

  mounted() {
    this.initDatabase({accountBookName: this.bookName});
  },

  computed: {
    // table data
    // tableData() {
    //   console.log(this)
    //   const { pageIndex, pageSize } = this;
    //   return DATA.slice((pageIndex - 1) * pageSize, pageIndex * pageSize);
    // },
    // total count
    totalCount() {
      return DATA.length;
    },
  },

  methods: {
    pageNumberChange(pageIndex) {
      this.pageIndex = pageIndex;
      if(DATA.length > 0){
        this.tableData = DATA.slice((pageIndex - 1) * this.pageSize, pageIndex * this.pageSize);
      }

      return DATA.slice((pageIndex - 1) * this.pageSize, pageIndex * this.pageSize);

    },

    pageSizeChange(pageSize) {
      this.pageIndex = 1;
      this.pageSize = pageSize;
    },

    initDatabase(searchForm) {
      findContent(searchForm)
          .then(data => {
            DATA = data.body
            this.tableData = this.pageNumberChange(1)
            this.total = data.total

            if(data.total === 0){
              this.showEmpty = true;
            }else{
              this.showEmpty = false;
            }
      })

    },

    search(searchForm){
      searchForm.accountBookName = this.bookName
      this.initDatabase(searchForm)
    }


  }
}
</script>

<style>

.table_data_container{
  height: 50vh;
}

.table-pagination{
  margin-top: 2vh;
}

.ve-pagination .ve-pagination-li {
  /*min-width: 5vw !important;*/
  font-size: 14px !important;
  line-height: 4vh !important;
}

.ve-table-body-td {
  font-size: 0.7vw !important;
}

.ve-table-header {
  font-size: 1vw !important;
}

.ve-pagination-li {
  min-width: 1.5vw !important;
  height: 4vh !important;
}

.empty-data {
  display: flex;
  align-items: center;
  justify-content: center;
  height: calc(100% - 10vh);
  width: 100%;
  color: #666;
  font-size: 16px;
  border: 1px solid #eee;
  border-top: 0;
}
</style>