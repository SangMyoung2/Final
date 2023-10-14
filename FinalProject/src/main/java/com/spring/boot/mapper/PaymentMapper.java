package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Mapper
public interface PaymentMapper {
    void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO);
    void updateUserPoint(userPointDTO userPointDTO);  // 포인트 업데이트 메서드
    int getMeetMoney(int meetListNum); // 모임의 meetMoney 조회
    int getUserPoint(String email); // 사용자의 포인트 잔액 조회
}
