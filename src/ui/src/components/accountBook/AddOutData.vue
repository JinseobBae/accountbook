<template>
<div>
  <h1>출금</h1>
  <form v-on:submit="submit">

    <h4>대분류</h4>
    <select v-model="selectedCategory1"  @change="categoryChanged">
      <option :value="null" disabled>카테고리를 선택하세요.</option>
      <option v-for="category in category1List" v-bind:key="category.type + '||' + category.code" v-bind:value="category.code">
        {{category.name}}
      </option>
    </select>

    <h4>소분류</h4>
    <select v-model="selectedCategory2" :disabled="category2Disabled">
      <option :value="null" disabled>카테고리를 선택하세요.</option>
      <option v-for="category in category2List" v-bind:key="category.type + '||' + category.code + '||' + category.subCode" v-bind:value="category.subCode">
        {{category.subName}}
      </option>
    </select>

    <h4>사용처</h4>
    <input id="title" v-model="formData.title" placeholder="사용처를 입력해주세요." type="text">

    <h4>금액</h4>
    <input id="amount" v-model="formData.amount" placeholder="금액을 입력해주세요." type="number">

    <h4>사용일자</h4>
    <input id="dayOfUse" v-model="formData.realUseDt" placeholder="날짜을 입력해주세요." type="date">

    <h4>비고</h4>
    <input id="note" v-model="formData.note" placeholder="메모를 입력해주세요." type="text">

    <div class="btn-block">
      <button type="submit">등록</button>
    </div>

  </form>
</div>
</template>

<script>

import {findCategories, saveData} from "@/api/CategoryApi";

export default {
  name: "AddData",
  data(){
    return{
      selectedCategory1 : '',
      selectedCategory2 : '',
      originCategory2List: [],
      category1List: [],
      category2List: [],
      category2Disabled: true,
      formData: {realUseDt: new Date().toISOString().slice(0,10)}
    }
  },
  methods:{
    submit(evt){
      evt.preventDefault();

      const done = confirm("등록하시겠습니까?")

      if(done){
        this.formData.type = '1'
        this.formData.category1 = this.selectedCategory1;
        this.formData.category2 = this.selectedCategory2;
        this.formData.accountBookName = '집';

        saveData(this.formData)

        this.$emit('success')
      }

    },
    typeChanged(){
      this.category1List = []
      this.category2List = []
      this.category1Disabled = false;

      if(!this.category2Disabled){
        this.category2Disabled = true;
      }


      this.originCategory1List.forEach((oc) => {
        if(oc.type == this.selectedType){
          this.category1List.push(oc)
        }
      })
    },

    categoryChanged(){
      this.category2Disabled = false;
      this.category2List = []

      this.originCategory2List.forEach((oc2) => {
        if((oc2.type + '||' + oc2.code) === ('1' + '||' + this.selectedCategory1)){
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
    }
  },

  mounted() {
    findCategories().then(data => {
      const categoryData = data.body

      categoryData.forEach((category) => {
        if(!this.checkDup(this.category1List, category)){
          this.category1List.push({type: category.type, code: category.code, name: category.name})
        }
      })

      this.originCategory2List = categoryData

    })

  }

}
</script>

<style scoped>
body, div, form, input, select {
  padding: 0;
  margin: 0;
  outline: none;
  font-family: Roboto, Arial, sans-serif;
  font-size: 15px;
  color: #666;
  line-height: 22px;
}
input, select {
  width: calc(100% - 100px);
  padding: 3px;
  border: 1px solid #ccc;
  border-radius: 3px;
  vertical-align: middle;
}
input:hover, textarea:hover, select:hover {
  outline: none;
  border: 1px solid #0cd4e0;
  /*background: #ece6e6;*/
  background: #f6f3f3;
}
.btn-block {
  margin-top: 20px;
  text-align: center;
}

</style>