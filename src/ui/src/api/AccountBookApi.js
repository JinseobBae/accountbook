import axios from "axios";

const findBookByName = (bookName) => {

    return axios.get("book/account-book",{
        params : {
            bookName : bookName
        }
    }).then( response => {
        return response.data
    }).catch( error => {
        alert(error)
    })

}

export { findBookByName }