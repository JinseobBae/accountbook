import axios from "axios";

const fetchNaviData= () => {

    const nav = {
        elementId: "main-navbar",
        isUsingVueRouter: true,
        menuOptionsLeft: [
            {
                type: "spacer",
                text: "",
                path: ""
            },
            {
                type: "spacer",
                text: "",
                path: ""
            },
            {
                type: "link",
                text: "가계부",
                path: "/"
            },
            {
                type: "spacer",
                text: "",
                path: ""
            },
            {
                type: "spacer",
                text: "",
                path: ""
            },
            {
                type: "link",
                text: "통계",
                path: "/statistic"
            },
        ],
    }

    return nav
}

export { fetchNaviData }