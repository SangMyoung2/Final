package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.mapper.PaymentMapper;

import com.spring.boot.dto.PaymentInfoDTO;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void processPaymentInfo(PaymentInfoDTO paymentInfoDTO){
        try {
            paymentMapper.insertPaymentInfo(paymentInfoDTO);
            
        } catch (Exception e) {
            e.printStackTrace(); // 이렇게 해서 로그에 어떤 예외가 출력 되는지 확인
            throw e;
        }
    }
}