<template>
  <div style="width: inherit">
    <form v-on:submit="submit" class="data_search_container">

      <span>
        종류 :
        <select v-model="searchData.type" @change="typeChanged">
          <option :value="null" disabled >타입 선택하세요.</option>
          <option :value='0' >입금</option>
          <option :value='1' >출금</option>
        </select>

      </span>
      <span>
        카테고리1 :
        <select v-model="searchData.category1" @change="categoryChanged">
          <option :value="null" disabled>카테고리를 선택하세요.</option>
          <option v-for="category in category1List" v-bind:key="category.type + '||' + category.code" v-bind:value="category.code">
            {{category.name}}
          </option>
        </select>
      </span>
      <span>
        카테고리2 :
        <select v-model="searchData.category2">
          <option :value="null" disabled>카테고리를 선택하세요.</option>
          <option v-for="category in category2List" v-bind:key="category.type + '||' + category.code + '||' + category.subCode" v-bind:value="category.subCode">
            {{category.subName}}
          </option>
        </select>
      </span>
      <span>
        항목 : <input type="text" v-model="searchData.contentTitle" placeholder=""/>
      </span>
      <span>
        사용일자 : <input type="date" v-model="searchData.startDate" placeholder=""/> ~ <input type="date" v-model="searchData.endDate" placeholder=""/>
      </span>
      <span>
        <button type="button" @click="cleanSearch">초기화</button>
      </span>
      <span>
        <button type="submit">검색</button>
      </span>
    </form>
  </div>
</template>

<script>
import {findCategories} from "@/api/CategoryApi";

export default {
  name: "DataSearchBar",
  data(){
    return {
      searchData: {},
      originCategory1List: [],
      originCategory2List: [],
      category1List: [],
      category2List: [],
      category1Disabled: true,
      category2Disabled: true
    }
  },
  mounted() {
    findCategories().then(data => {
      const categoryData = data.body

      categoryData.forEach((category) => {
        if(!this.checkDup(this.originCategory1List, category)){
          this.originCategory1List.push({type: category.type, code: category.code, name: category.name})
        }
      })

      this.originCategory2List = categoryData

    })
  },
  methods: {
    submit(evt){
      evt.preventDefault()
      this.$emit("search", this.searchData)
    },
    typeChanged(){
      this.category1List = []
      this.category2List = []
      this.category1Disabled = false;

      if(!this.category2Disabled){
        this.category2Disabled = true;
      }


      this.originCategory1List.forEach((oc) => {
        if(oc.type == this.searchData.type){
          this.category1List.push(oc)
        }
      })
    },

    categoryChanged(){
      this.category2Disabled = false;
      this.category2List = []

      this.originCategory2List.forEach((oc2) => {
        if((oc2.type + '||' + oc2.code) === (this.searchData.type + '||' + this.searchData.category1)){
          this.category2List.push(oc2)
        }
      })
    },

    checkDup(source, target){
      let isDup = false;

      source.forEach((s) => {
        if((s.type + '||' + s.code) === (target.type + '||' + target.code)){
          isDup = true;
        }
      })

      return isDup
    },

    cleanSearch(){
      this.$emit("clean")
    }

  }
}
</script>

<style scoped>

.data_search_container{
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  flex-wrap: wrap;
}


</style>