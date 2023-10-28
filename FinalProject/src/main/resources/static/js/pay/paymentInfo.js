$(document).ready(function() {
    // 초기 설정
    checkAndDisplayNoResultsMessage();

    // 포인트 충전 내역 버튼 클릭 시
    $('#show-pay-history').click(function() {
        resetFilters();
        $('.paymentinfocontainer').show();
        $('.pointinfocontainer').hide();
        $('.paytitle').text('포인트 충전 내역');
        checkAndDisplayNoResultsMessage();
    });

    // 포인트 사용 내역 버튼 클릭 시
    $('#show-point-history').click(function() {
        resetFilters();
        $('.paymentinfocontainer').hide();
        $('.pointinfocontainer').show();
        $('.paytitle').text('포인트 사용 내역');
        checkAndDisplayNoResultsMessage();
    });

    // 필터 적용
    $('#filter-btn').click(function() {
        var startDateInput = $('#start-date').val();
        var endDateInput = $('#end-date').val();
        var startDate = new Date(startDateInput).getTime();
        var endDate = new Date(endDateInput).getTime();

        resetFilters();
        if ($('.paytitle').text() === '포인트 충전 내역') {
            filterResults('.paymentinfocontainer', '.label:contains("결제 날짜/시간:")', startDate, endDate);
        } else {
            filterResults('.pointinfocontainer', '.label:contains("사용 날짜/시간:")', startDate, endDate);
        }
    });

    function resetFilters() {
        $('.paymentinfocontainer, .pointinfocontainer').hide();
        $('#no-payment-results-message, #no-use-results-message').hide();
    }

    function filterResults(containerClass, labelSelector, startDate, endDate) {
        var hasResults = false;
        $(containerClass).each(function() {
            var dateText = $(this).find(labelSelector).next().text();
            var date = new Date(dateText).getTime();

            if (date >= startDate && date <= endDate) {
                $(this).show();
                hasResults = true;
            }
        });

        if (!hasResults) {
            if (containerClass === '.paymentinfocontainer') {
                $('#no-payment-results-message').show();
            } else {
                $('#no-use-results-message').show();
            }
        }
    }

    function checkAndDisplayNoResultsMessage() {
        if ($('.paymentinfocontainer:visible').length === 0 && $('.paytitle').text() === '포인트 충전 내역') {
            $('#no-payment-results-message').show();
        } else if ($('.pointinfocontainer:visible').length === 0 && $('.paytitle').text() === '포인트 사용 내역') {
            $('#no-use-results-message').show();
        } else {
            $('#no-payment-results-message, #no-use-results-message').hide();
        }
    }

    // useType 값에 따른 텍스트 변환
    $('.pointinfocontainer').each(function() {
        var useTypeText = $(this).find('.label:contains("구분:")').next().text();
        switch(useTypeText) {
            case '1':
                $(this).find('.label:contains("구분:")').next().text('사용');
                break;
            case '2':
                $(this).find('.label:contains("구분:")').next().text('충전');
                break;
            case '3':
                $(this).find('.label:contains("구분:")').next().text('환불');
                break;
        }
    });
});
