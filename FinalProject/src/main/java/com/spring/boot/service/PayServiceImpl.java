package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dto.PayDTO;
import com.spring.boot.mapper.PayMapper;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayMapper payMapper; // PayMapper 주입

    @Override
    @Transactional // 트랜잭션 관리를 위해 @Transactional 어노테이션을 사용합니다.
    public PayDTO processPayment(PayDTO payDTO) {
        // 여기에서 실제 결제 처리 로직을 구현합니다.
        // payDTO를 사용하여 결제를 처리하고 결과를 반환합니다.

        // 결제 처리가 성공하면 결제 상태 업데이트
        payDTO.setPaymentsStatus("SUCCESS");

        // 결제 정보를 데이터베이스에 저장
        payMapper.insertPayment(payDTO);

        // 결제 정보를 포함한 DTO를 반환
        return payDTO;
    }

    // 추가적인 결제 관련 메서드를 필요에 따라 정의할 수 있습니다.
}