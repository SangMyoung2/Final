
function requestPay(amount) {
    var IMP = window.IMP;
    IMP.init('imp27175164');
    IMP.request_pay({
        pg: "html5_inicis",
        merchant_uid: createOrderNum(),
        name: "가치페이 " + amount,
        amount: amount,
        buyer_name: "배수지",
        buyer_email: "djwotjr5667@naver.com",
        buyer_tel: "010-1234-5678",
    },
    rsp => {
        console.log('결제 응답:', rsp);
        if (rsp.success) {
            const payMethodUsed = rsp.pay_method; // 실제로 사용된 결제 수단을 가져옵니다.
        
            let dataToSend = {
                user_email:"djwotjr5667@naver.com",
                paid_amount: rsp.paid_amount,
                paid_at: rsp.paid_at,
                name: rsp.name,
                status: rsp.status,
                pay_method: payMethodUsed
            };
        
            // 카드로 결제한 경우만 해당 정보를 추출합니다.
            if (payMethodUsed === 'card') {
                dataToSend.card_name = rsp.card_name;
                dataToSend.card_number = rsp.card_number;
                dataToSend.apply_num = rsp.apply_num;
            }
        
            // 다른 결제 수단에 대한 정보 추출 로직도 여기에 추가할 수 있습니다.
        
            axios({
                url: "/payment-info",
                method: "post",
                headers: {"Content-Type" : "application/json"},
                data: dataToSend
            })
            .then((response) => {
                console.log("DB 저장 응답:", response);
                window.location.href = "/paySuccessPage";
                // alert('결제를 성공했습니다.')
            })
            .catch(error => {
                console.error("DB 저장 에러: ", error);
                alert('DB 저장 중 문제가 발생하였습니다.')
            });
        } else {
        window.location.href = "/payFailurePage";
        alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
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
