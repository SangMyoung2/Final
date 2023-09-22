
$(document).on({
    
    click: function (evt) {
        var btn = $("#check_module5")
        console.log(evt.target)
        if(evt.target.id !== 'check_module5') return

    
    // alert('abcsd')
    var IMP = window.IMP; // 생략가능
    IMP.init('imp27175164');
    // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
    // i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
    IMP.request_pay({
        pg: 'html5_inicis', // version 1.1.0부터 지원.
        /* 
            'kakao':카카오페이, 
            html5_inicis':이니시스(웹표준결제)
                'nice':나이스페이
                'jtnet':제이티넷
                'uplus':LG유플러스
                'danal':다날
                'payco':페이코
                'syrup':시럽페이
                'paypal':페이팔
            */
        pay_method: 'card',
        /* 
            'samsung':삼성페이, 
            'card':신용카드, 
            'trans':실시간계좌이체,
            'vbank':가상계좌,
            'phone':휴대폰소액결제 
        */
        merchant_uid: 'merchant_' + new Date().getTime(),
        /* 
            merchant_uid에 경우 
            https://docs.iamport.kr/implementation/payment
            위에 url에 따라가시면 넣을 수 있는 방법이 있습니다.
            참고하세요. 
            나중에 포스팅 해볼게요.
         */
        name: '가치페이 5,000',
        //결제창에서 보여질 이름
        amount: 50, 
        //가격 
        buyer_email: 'iamport@siot.do',
        buyer_name: '구매자이름5',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        m_redirect_url: 'https://www.yourdomain.com/payments/complete'
        /*  
            모바일 결제시,
            결제가 끝나고 랜딩되는 URL을 지정 
            (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
            */
    }, function (rsp) {
        console.log(rsp);
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + rsp.imp_uid;
            msg += '상점 거래ID : ' + rsp.merchant_uid;
            msg += '결제 금액 : ' + rsp.paid_amount;
            msg += '카드 승인번호 : ' + rsp.apply_num;
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
    });
}});


$(document).on({
    
    click: function (evt) {
        var btn = $("#check_module10")
        console.log(evt.target)
        if(evt.target.id !== 'check_module10') return

    
    // alert('abcsd')
    var IMP = window.IMP; // 생략가능
    IMP.init('imp27175164');
    // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
    // i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
    IMP.request_pay({
        pg: 'html5_inicis', // version 1.1.0부터 지원.
        /* 
            'kakao':카카오페이, 
            html5_inicis':이니시스(웹표준결제)
                'nice':나이스페이
                'jtnet':제이티넷
                'uplus':LG유플러스
                'danal':다날
                'payco':페이코
                'syrup':시럽페이
                'paypal':페이팔
            */
        pay_method: 'card',
        /* 
            'samsung':삼성페이, 
            'card':신용카드, 
            'trans':실시간계좌이체,
            'vbank':가상계좌,
            'phone':휴대폰소액결제 
        */
        merchant_uid: 'merchant_' + new Date().getTime(),
        /* 
            merchant_uid에 경우 
            https://docs.iamport.kr/implementation/payment
            위에 url에 따라가시면 넣을 수 있는 방법이 있습니다.
            참고하세요. 
            나중에 포스팅 해볼게요.
         */
        name: '가치페이 10,000',
        //결제창에서 보여질 이름
        amount: 100, 
        //가격 
        buyer_email: 'iamport@siot.do',
        buyer_name: '구매자이름10',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        m_redirect_url: 'https://www.yourdomain.com/payments/complete'
        /*  
            모바일 결제시,
            결제가 끝나고 랜딩되는 URL을 지정 
            (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
            */
    }, function (rsp) {
        console.log(rsp);
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + rsp.imp_uid;
            msg += '상점 거래ID : ' + rsp.merchant_uid;
            msg += '결제 금액 : ' + rsp.paid_amount;
            msg += '카드 승인번호 : ' + rsp.apply_num;
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
    });
}});

$(document).on({
    
    click: function (evt) {
        var btn = $("#check_module30")
        console.log(evt.target)
        if(evt.target.id !== 'check_module30') return

    
    // alert('abcsd')
    var IMP = window.IMP; // 생략가능
    IMP.init('imp27175164');
    // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
    // i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
    IMP.request_pay({
        pg: 'html5_inicis', // version 1.1.0부터 지원.
        /* 
            'kakao':카카오페이, 
            html5_inicis':이니시스(웹표준결제)
                'nice':나이스페이
                'jtnet':제이티넷
                'uplus':LG유플러스
                'danal':다날
                'payco':페이코
                'syrup':시럽페이
                'paypal':페이팔
            */
        pay_method: 'card',
        /* 
            'samsung':삼성페이, 
            'card':신용카드, 
            'trans':실시간계좌이체,
            'vbank':가상계좌,
            'phone':휴대폰소액결제 
        */
        merchant_uid: 'merchant_' + new Date().getTime(),
        /* 
            merchant_uid에 경우 
            https://docs.iamport.kr/implementation/payment
            위에 url에 따라가시면 넣을 수 있는 방법이 있습니다.
            참고하세요. 
            나중에 포스팅 해볼게요.
         */
        name: '가치페이 30,000',
        //결제창에서 보여질 이름
        amount: 300, 
        //가격 
        buyer_email: 'iamport@siot.do',
        buyer_name: '구매자이름30',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        m_redirect_url: 'https://www.yourdomain.com/payments/complete'
        /*  
            모바일 결제시,
            결제가 끝나고 랜딩되는 URL을 지정 
            (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
            */
    }, function (rsp) {
        console.log(rsp);
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + rsp.imp_uid;
            msg += '상점 거래ID : ' + rsp.merchant_uid;
            msg += '결제 금액 : ' + rsp.paid_amount;
            msg += '카드 승인번호 : ' + rsp.apply_num;
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
    });
}});

$(document).on({
    
    click: function (evt) {
        var btn = $("#check_module50")
        console.log(evt.target)
        if(evt.target.id !== 'check_module50') return

    
    // alert('abcsd')
    var IMP = window.IMP; // 생략가능
    IMP.init('imp27175164');
    // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
    // i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드
    IMP.request_pay({
        pg: 'html5_inicis', // version 1.1.0부터 지원.
        /* 
            'kakao':카카오페이, 
            html5_inicis':이니시스(웹표준결제)
                'nice':나이스페이
                'jtnet':제이티넷
                'uplus':LG유플러스
                'danal':다날
                'payco':페이코
                'syrup':시럽페이
                'paypal':페이팔
            */
        pay_method: 'card',
        /* 
            'samsung':삼성페이, 
            'card':신용카드, 
            'trans':실시간계좌이체,
            'vbank':가상계좌,
            'phone':휴대폰소액결제 
        */
        merchant_uid: 'merchant_' + new Date().getTime(), 
        /* 
            결제 요청 시, 가맹점에서 아임포트로 전달한 가맹점 고유 주문번호
            - 결제완료 (paid/ 빌링키 발급 포함)된 거래에 사용된 적있는 merchant_uid는 재사용이 불가
            - 가맹점 서비스의 DB정보와 아임포트 서비스에 저장 된 정보 간 비교를 할 때 사용 주문번호를 기준으로
            결제된 내역을 확인 하는 경우 사용
         */
        name: '가치페이 50,000',
        //결제창에서 보여질 이름
        amount: 500, 
        //가격 
        buyer_email: 'iamport@siot.do',
        buyer_name: '구매자이름50',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456',
        m_redirect_url: 'https://www.yourdomain.com/payments/complete'
        /*  
            모바일 결제시,
            결제가 끝나고 랜딩되는 URL을 지정 
            (카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐) 
            */
    }, function (rsp) {
        console.log(rsp);
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + rsp.imp_uid;
            msg += '상점 거래ID : ' + rsp.merchant_uid;
            msg += '결제 금액 : ' + rsp.paid_amount;
            msg += '카드 승인번호 : ' + rsp.apply_num;
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
    });
}});

$(".hover").mouseleave(
    function() {
      $(this).removeClass("hover");
    }
  );