import axios from "axios";

const findContent = (searchFormData) => {
    return axios.get('book/content', {
        params : searchFormData
    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error.response.data.message)
    })
}

export {findContent}