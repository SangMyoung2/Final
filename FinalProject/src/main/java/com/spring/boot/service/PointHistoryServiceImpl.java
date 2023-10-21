package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.PointHistoryDTO;
import com.spring.boot.mapper.PointHistoryMapper;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {

    @Autowired
    private PointHistoryMapper pointHistoryMapper;

    @Override
    public void insertPointHistory(PointHistoryDTO dto){
        pointHistoryMapper.insertPointHistory(dto);
    }

    @Override
    public PointHistoryDTO getUseReadData(PointHistoryDTO dto){
        return pointHistoryMapper.getUseReadData(dto);
    }
}
