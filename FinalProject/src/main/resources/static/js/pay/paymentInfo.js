$(document).ready(function() {
    // 초기 설정
    checkAndDisplayNoResultsMessage();

    $('.paymentinfocontainer').show();
    $('.pointinfocontainer').hide();

    // 포인트 충전 내역 버튼 클릭 시
    $('#show-pay-history').click(function() {
        resetFilters();
        $('.paymentinfocontainer').show();
        $('.pointinfocontainer').hide();
        $('.paytitle').text('포인트 충전 내역');
        checkAndDisplayNoResultsMessage();
        resetLoadMore();
        loadMore();
    });

    // 포인트 사용 내역 버튼 클릭 시
    $('#show-point-history').click(function() {
        resetFilters();
        $('.paymentinfocontainer').hide();
        $('.pointinfocontainer').show();
        $('.paytitle').text('포인트 사용 내역');
        checkAndDisplayNoResultsMessage();
        resetLoadMore();
        loadMore();
    });

    $('#filter-btn').click(function() {
        var startDateInput = $('#start-date').val();
        var endDateInput = $('#end-date').val();

        if (!startDateInput && !endDateInput) {
            displayAllData();
            return;
        }

        var startDate = new Date(startDateInput).getTime();
        var endDate = new Date(endDateInput).getTime();

        if (startDate > endDate) {
            alert('시작 날짜가 종료 날짜보다 늦게 설정될 수 없습니다.');
            displayAllData();
            return;
        }

        resetFilters();
        if ($('.paytitle').text() === '포인트 충전 내역') {
            $('.pointinfocontainer').hide(); // 포인트 사용 내역 숨김
            filterResults('.paymentinfocontainer', '.label:contains("결제 날짜/시간:")', startDate, endDate);
        } else {
            $('.paymentinfocontainer').hide(); // 포인트 충전 내역 숨김
            filterResults('.pointinfocontainer', '.label:contains("사용 날짜/시간:")', startDate, endDate);
        }
        resetLoadMore();
    });

    function displayAllData() {
        resetFilters();
        if ($('.paytitle').text() === '포인트 충전 내역') {
            $('.paymentinfocontainer').show();
            $('.pointinfocontainer').hide(); // 포인트 사용 내역 숨김
        } else {
            $('.pointinfocontainer').show();
            $('.paymentinfocontainer').hide(); // 포인트 충전 내역 숨김
        }
        checkAndDisplayNoResultsMessage();
        resetLoadMore();
        loadMore();
    }

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
