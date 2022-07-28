// eslint-disable-next-line no-unused-vars
export default [
    {field: "category1", key: "category1", title: "대분류", align: "center", width: "10%"},
    {field: "category2", key: "category2", title: "소분류", align: "center", width: "10%"},
    {field: "title", key: "title", title: "항목", align: "center", width: "30%"},
    {
        field: "amount", key: "amount", title: "금액", align: "center", width: "10%", renderBodyCell: ({row, column, rowIndex}, h) => {
            const formattedAmount = setComma(row.amount)
            if(row.type === '0'){
                return (
                    <span style="color:#1890ff;">
                        + {formattedAmount}
                    </span>
                );
            }else{
                return (
                    <span style="color:red;">
                        - {formattedAmount}
                    </span>
                );
            }
        }
    },
    {field: "realUseDt", key: "usedDate", title: "사용일자", align: "center", width: "15%"},
    {field: "note", key: "note", title: "비고", align: "center", width: "25%"}
];

const setComma = (number) => {
    return number.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")
}
