$(document).ready(function() {
    // 초기 화면에서는 포인트 충전 내역만 보이도록 설정
    $('.paymentinfocontainer').show();
    $('.pointinfocontainer').hide();

    // 포인트 충전 내역 버튼 클릭 시
    $('#show-pay-history').click(function() {
        $('.paymentinfocontainer').show();
        $('.pointinfocontainer').hide();
        $('#title-text').text('포인트 충전 내역'); // 텍스트 변경
    });

    // 포인트 사용 내역 버튼 클릭 시
    $('#show-point-history').click(function() {
        $('.paymentinfocontainer').hide();
        $('.pointinfocontainer').show();
        $('#title-text').text('포인트 사용 내역'); // 텍스트 변경
    });

    $('#filter-btn').click(function() {
        var startDateInput = $('#start-date').val();
        var endDateInput = $('#end-date').val();
        var hasResults = false;

        // 모든 paymentinfocontainer와 pointinfocontainer 요소와 메시지를 숨김
        $('.paymentinfocontainer, .pointinfocontainer').hide();
        $('#no-results-message').hide();

        // 날짜 입력이 없으면 모든 내역을 보여줌
        if (!startDateInput || !endDateInput) {
            $('.paymentinfocontainer, .pointinfocontainer').show();
            return;
        }

        var startDate = new Date(startDateInput).getTime();
        var endDate = new Date(endDateInput).setHours(23, 59, 59, 999);

        if (startDate > endDate) {
            alert("시작 날짜는 종료 날짜보다 늦을 수 없습니다.");
            $('.paymentinfocontainer, .pointinfocontainer').show();
            return;
        }

        // 선택한 범위 내의 결제 내역만 표시
        $('.paymentinfocontainer').each(filterResults);
        $('.pointinfocontainer').each(filterResults);

        if (!hasResults) {
            $('#no-results-message').show();
        }

        function filterResults() {
            var dateText = $(this).find('.label:contains("결제 날짜/시간:")').next().text() || 
                           $(this).find('.label:contains("사용 날짜/시간:")').next().text();

            var date = new Date(dateText).getTime();

            if (date >= startDate && date <= endDate) {
                $(this).show();
                hasResults = true;
            }
        }
    });
});