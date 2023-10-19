package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.CustomerCenterDTO;

@Mapper
public interface CustomerCenterMapper {
    
    // 모든 질문과 답변을 띄우기
    public List<CustomerCenterDTO> getAllList();




}
