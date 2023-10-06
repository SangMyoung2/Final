package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.mapper.PaymentMapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){

        System.out.println("Paid Amount: " + paymentInfoDTO.getPaid_amount());

        try {
            paymentMapper.insertPaymentInfo(paymentInfoDTO);
            
            // 결제 정보 처리 후, UserPoint 업데이트
            // 임의의 이메일 주소 가져오기 (이 부분은 단순화된 예시입니다. 실제로는 이메일 주소를 DB에서 조회해야 합니다.)
            String userEmail = "test@email.com"; 
            userPointDTO userPointDTO = new userPointDTO();
            userPointDTO.setUser_email(userEmail); 
            userPointDTO.setPoint_balance(paymentInfoDTO.getPaid_amount());
            updateUserPoint(userPointDTO);
            
        } catch (Exception e) {
            e.printStackTrace(); // 이렇게 해서 로그에 어떤 예외가 출력 되는지 확인
            throw e;
        }
    }

    @Override
    public void updateUserPoint(userPointDTO userPointDTO) {
        paymentMapper.updateUserPoint(userPointDTO);
}

}