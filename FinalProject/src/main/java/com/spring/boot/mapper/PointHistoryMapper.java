package com.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.PointHistoryDTO;

@Mapper
public interface PointHistoryMapper {
    public void insertPointHistory(PointHistoryDTO dto);
    public PointHistoryDTO getUseReadData(PointHistoryDTO dto);
}
