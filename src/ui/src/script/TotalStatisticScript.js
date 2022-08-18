export {genMonthlyBarCharData, genMonthlyBarCharDataDetail}

import {groupBy, hexToRgbA} from "@/common/CommonJs";

const backgroundColors = [
    '#41B883', '#E46651', '#00D8FF', '#DD1B16'
    , '#7442e5', '#b4a9a9', '#debf76', '#16dd70'
    , '#2a6dde', '#e7791d', '#3c4787', '#875d3c'
    ,'#9a6868' , '#4c9366', '#b63aaf', '#cf73e2'];

function genMonthlyBarCharData(data) {

    const labels = []
    const chartData = []
    let dataLabel = ''

    data.forEach((d) => {
        labels.push(d.label)
        chartData.push(d.totalAmount)

        if (dataLabel === '') {
            if (d.type === null) {
                dataLabel = '사용금액'
            } else {
                dataLabel = d.type
            }
        }
    })

    return {
        labels: labels,
        datasets: [
            {
                label: dataLabel,
                backgroundColor: hexToRgbA(backgroundColors[2], 0.2),
                borderColor: hexToRgbA(backgroundColors[2],1),
                borderWidth: 1,
                data: chartData
            }
        ]
    }


}


function genMonthlyBarCharDataDetail(data) {

    const labels = []
    const dataSetArray = []
    let dataLabel = ''

    data.forEach((data) => {
        const label = data.label
        if(!labels.includes(label)){
            labels.push(label)
        }
    })

    const monthlyData = groupBy(data, 'category2')

    console.log(monthlyData)
    console.log(labels)

    Object.keys(monthlyData).forEach((key, idx) => {
        const categoryData = monthlyData[key]
        const dataArray = []

        labels.forEach((label) => {
            let amount = 0
            categoryData.some((data) => {
                if(label === data.label){
                    amount = data.totalAmount
                    return true
                }
            })

            dataArray.push(amount)

        })

        dataSetArray.push({
            label : key,
            backgroundColor : hexToRgbA(backgroundColors[idx], 0.5),
            borderColor: hexToRgbA(backgroundColors[idx]),
            borderWidth: 1,
            borderSkipped: 'middle',
            data: dataArray
        })
    })

    return {
        labels: labels,
        datasets: dataSetArray
    }
}