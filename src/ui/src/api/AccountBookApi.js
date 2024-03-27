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

const doAuth = (key) => {

    return axios.post("book/auth",
        key,
        {
            headers: {
                'Content-Type' : 'application/json'
            }
        }
    ).then( response => {
        return response.data
    }).catch( error => {
        alert(error)
    })

}

export { findBookByName, doAuth }