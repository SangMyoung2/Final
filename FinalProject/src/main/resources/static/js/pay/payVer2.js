
function requestPay() {
    var IMP = window.IMP;
    IMP.init('imp27175164');
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: createOrderNum(),
        name: "가치페이1000",
        amount: 10,
        buyer_name: "배수지",
        buyer_email: "djwotjr5667@naver.com",
        buyer_tel: "010-1234-5678",
    },
    rsp => {
        console.log('결제 응답:', rsp);
        if (rsp.success) {
        // axios로 HTTP 요청
        const {paid_amount,paid_at,card_name,card_number,apply_num,name,status} = rsp;

        axios({
            url: "/payment-info",
            method: "post",
            headers: {"Content-Type" : "application/json"},
            data: {
                paid_amount,
                paid_at,
                card_name,
                card_number,
                apply_num,
                name,
                status
            }
        }).then((response) => {
            console.log("DB 저장 응답:", response);
            // 서버 결제 API 성공시 로직
            // 다른 추가 로직 (예: 사용자를 결제 성공 페이지로 리다이렉트)
            window.location.href = "/paySuccessPage";

            alert('결제를 성공했습니다.')
            
        }).catch(error => {
            console.error("DB 저장 에러: ", error);
            alert('DB 저장 중 문제가 발생하였습니다.')
        });
    } else {
        window.location.href = "/payFailurePage";
        alert('결제에 실패했습니다. 에러 내용: ${rsp.error_msg}');
    }
});
}

function createOrderNum(){
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2,"0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for(let i=0; i<10; i++) {
        orderNum += Math.floor(Math.random() * 8);
    }

    return orderNum;

}
