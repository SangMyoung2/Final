
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
        buyer_addr: "서울특별시 강남구 삼성동",
        buyer_postcode: "123-456"
    },
    rsp => {
        console.log('결제 응답:', rsp);
        if (rsp.success) {
        // axios로 HTTP 요청
        axios({
            url: "{서버의 결제 정보를 받는 endpoint}",
            method: "post",
            headers: {"Content-Type" : "application/json"},
            data: {
                imp_uid: rsp.imp_uid,
                merchant_uid: rsp.merchant_uid
            }
        }).then((response) => {
            console.log("서버 응답:", response);
            // 서버 결제 API 성공시 로직
            // 다른 추가 로직 (예: 사용자를 결제 성공 페이지로 리다이렉트)
            window.location.assign("/paySuccessPage.html");
            
        })
    } else {
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
