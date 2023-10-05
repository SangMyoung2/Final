package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;
import com.spring.boot.dto.PointDTO;

@Mapper
public interface PaymentMapper {
    void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO);

    // 사용자 포인트 업데이트를 위한 메서드를 추가합니다.
    void updateUserPoints(PointDTO PointDTO);
}
