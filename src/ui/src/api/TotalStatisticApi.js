import axios from "axios";

const getLastMonthCompare = (searchFormData) => {
    return axios.get('statistic/with-last-month', {
        params : searchFormData
    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error.response.data.message)
    })
}

const getLastMonthCompareDetail = (searchFormData) => {
    return axios.get('statistic/with-last-month-detail', {
        params : searchFormData
    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error.response.data.message)
    })
}

export {
    getLastMonthCompare, getLastMonthCompareDetail
}