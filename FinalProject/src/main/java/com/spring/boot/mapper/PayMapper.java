package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PayDTO;

@Mapper
public interface PayMapper {
    void insertPay(PayDTO paymentDTO);
}
