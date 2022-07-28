import axios from "axios";

const getContentStatistic = (searchFormData) => {
    return axios.get('book/content/statistic', {
        params : searchFormData
    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error.response.data.message)
    })
}

export {getContentStatistic}