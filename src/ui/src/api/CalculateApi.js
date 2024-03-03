import axios from "axios";

const findMonthlyCalculated = () => {
    return axios.get('calculate/calculate-last-six-month')
        .then( response => {
        return response.data
        }).catch( error => {
            alert(error.response.data.message)
        })
}

export {findMonthlyCalculated}
