import Vue from 'vue'
import Router from 'vue-router'
import AccountBook from "@/components/accountBook/AccountBook";
import TotalStatistic from "@/components/statistic/TotalStatistic";
import CalculateMain from "@/components/calculate/CalculateMain";


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
            component : TotalStatistic
        },
        {
            path : '/calculate',
            name : 'CalculateMain',
            component : CalculateMain
        },
    ]
})

