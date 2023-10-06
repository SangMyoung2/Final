package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.userPointDTO;


@Mapper
public interface PaymentMapper {
    void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO);
    void updateUserPoint(userPointDTO userPointDTO);  // 포인트 업데이트 메서드

}
