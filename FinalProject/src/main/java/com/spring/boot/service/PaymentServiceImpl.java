package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.mapper.PaymentMapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.PointDTO;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){
        try {
            paymentMapper.insertPaymentInfo(paymentInfoDTO);

            // 사용자의 이메일을 얻어오는 로직이 필요합니다.
            // 예를 들어, 세션에서 사용자 이메일을 얻어온다고 가정하겠습니다.
            String userEmail = "example@example.com";  // 실제로는 세션 또는 다른 방법으로 이메일을 가져와야 합니다.

            // 포인트 업데이트 로직
            PointDTO pointDTO = new PointDTO();
            pointDTO.setUserEmail(userEmail);
            pointDTO.setPointBalance(paymentInfoDTO.getPaid_amount());  // 결제된 금액만큼 포인트를 증가시킵니다.

            paymentMapper.updateUserPoints(pointDTO);
            
        } catch (Exception e) {
            e.printStackTrace(); // 이렇게 해서 로그에 어떤 예외가 출력 되는지 확인
            throw e;
        }
    }
}