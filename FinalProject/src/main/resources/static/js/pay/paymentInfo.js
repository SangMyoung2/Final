function getPaymentInfo() {
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;

    // 서버로 시작 날짜와 종료 날짜를 전송
    axios.get('/paymentInfo.action', {
        params: {
            startDate: startDate,
            endDate: endDate
        }
    })
    .then(function (response) {
        // 조회 결과를 처리하는 코드
        // 예를 들어, 결과를 화면에 표시하거나 다른 동작을 수행할 수 있습니다.
        console.log(response.data);
    })
    .catch(function (error) {
        console.error(error);
    });
}
