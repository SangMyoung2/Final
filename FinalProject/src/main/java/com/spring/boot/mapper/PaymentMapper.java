package com.spring.boot.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Mapper
public interface PaymentMapper {

    public void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO) throws Exception;

    public void updateOrInsertUserPointWithMap(Map<String, Object> params) throws Exception;
    
    public int getUserPoint(String email) throws Exception; // 사용자의 포인트 잔액 조회

    public List<PaymentInfoDTO> findByEmail(String email) throws Exception; // 사용자의 결제내역 조회
    
    public void updateUserPoint(userPointDTO userPointDTO);  // 포인트 업데이트 메서드
    
    public void updateUserUsePoint(userPointDTO userPointDTO);
}
