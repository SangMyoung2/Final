$(document).ready(function() {
    $('#filter-btn').click(function() {
        var startDateInput = $('#start-date').val();
        var endDateInput = $('#end-date').val();
        var hasResults = false;

        // 모든 paymentinfocontainer 요소와 메시지를 숨김
        $('.paymentinfocontainer').hide();
        $('#no-results-message').hide();

        // 시작날짜나 종료날짜가 비어있으면 모든 결제 내역을 보여줌
        if (!startDateInput || !endDateInput) {
            $('.paymentinfocontainer').show();
            return;
        }

        var startDate = new Date(startDateInput).getTime();
        var endDate = new Date(endDateInput).setHours(23, 59, 59, 999); // 종료 날짜의 시간을 23시 59분 59초로 설정

        // 선택한 범위 내의 결제 내역만 표시
        $('.paymentinfocontainer').each(function() {
            var paidDate = new Date($(this).find('.label:contains("결제 날짜/시간:")').next().text()).getTime();

            if (paidDate >= startDate && paidDate <= endDate) {
                $(this).show();
                hasResults = true;
            }
        });

        // 결과가 없을 경우 메시지 표시
        if (!hasResults) {
            $('#no-results-message').show();
        }
    });
});
