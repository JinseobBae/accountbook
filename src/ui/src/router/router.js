import Vue from 'vue'
import Router from 'vue-router'
import AccountBook from "@/components/accountBook/AccountBook";
import BarChart from "@/components/chart/BarChart";


Vue.use(Router)

export default new Router({
    base : process.env.BASE_URL,
    routes: [
        {
            path : '/',
            name : 'Home',
            component : AccountBook
        },
        {
            path : '/statistic',
            name : 'Statistic',
            component : BarChart

        }
    ]
})

