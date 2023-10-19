package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Mapper
public interface PaymentMapper {
    public void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO);
    public void updateUserPoint(userPointDTO userPointDTO);  // 포인트 업데이트 메서드
    public int getMeetMoney(int meetListNum); // 모임의 meetMoney 조회
    public int getUserPoint(String email); // 사용자의 포인트 잔액 조회
    public void updateUserUsePoint(userPointDTO userPointDTO);
}
