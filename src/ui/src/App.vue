<template>
  <div id="app">
    <vue-navigation-bar :options="navBarData"/>
    <main id="main">
      <router-view/>
    </main>
  </div>
</template>

<script>
import { fetchNaviData } from "@/api/NavigationApi"

export default {
  name: 'App',
  components: {
  },
  data(){
    return {
      navBarData: fetchNaviData()
    }
  }
}
</script>

<style>
#main {
  margin-top: 50px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
  /*overflow: auto;*/
}

.vnb__menu-options__option__link{
  font-size: 1.2rem !important;
}
</style>
