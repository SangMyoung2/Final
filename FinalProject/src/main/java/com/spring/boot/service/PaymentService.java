package com.spring.boot.service;

import com.spring.boot.dto.PaymentInfoDTO;

public interface PaymentService {
    void processPaymentInfo(PaymentInfoDTO paymentInfoDTO);
    // 추가적인 결제 관련 메서드를 필요에 따라 정의할 수 있습니다.
}