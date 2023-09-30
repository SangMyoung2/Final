package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PaymentInfoDTO;

@Mapper
public interface PaymentMapper {
    void insertPaymentInfo(PaymentInfoDTO paymentInfoDTO);
}
