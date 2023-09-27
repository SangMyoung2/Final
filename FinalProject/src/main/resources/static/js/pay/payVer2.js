
var IMP = window.IMP;
IMP.init("imp27175164");

function requestPay() {
    IMP.request_pay({
        pg: "INIpayTest",
        pay_method: "card",
        merchant_uid: "57008833-33004",
        name: "가치페이1000",
        amount: 10,
        buyer_name: "배수지",
        buyer_tel: "010-1234-5678",
        buyer_addr: "서울특별시 강남구 삼성동",
        buyer_postcode: "123-456"
    },
    function (rsp) {
        if (rsp.success){
            console.log(rsp);
        } else {
            console.log(rsp);
        }
        // callback
        // rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
    }
    );
}