import Vue from 'vue'
import App from './App.vue'
import VueNavigationBar from "vue-navigation-bar";
import "vue-navigation-bar/dist/vue-navigation-bar.css";
import "vue-easytable/libs/theme-default/index.css";
import VueEasyTable from "vue-easytable"; // import library
import router from './router/router'
import { Bar } from 'vue-chartjs/legacy'

Vue.use(VueEasyTable)

Vue.config.productionTip = false

Vue.component("vue-navigation-bar", VueNavigationBar);

Vue.component("bar", Bar)


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
