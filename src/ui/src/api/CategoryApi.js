import axios from "axios";

const findCategories = () => {
    return axios.get('book/category', {

    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error.response.data.message)
    })
}

const saveData = (addData) => {
    axios.post(
        'book/content'
        ,addData
        ,{
            headers: {
                'Content-Type' : 'application/json'
            }
        }
    ).catch( error => {
        alert(error)
    })
}

export { findCategories, saveData }