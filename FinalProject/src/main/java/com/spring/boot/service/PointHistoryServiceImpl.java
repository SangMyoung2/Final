package com.spring.boot.service;

import java.util.List;

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
    public List<PointHistoryDTO> getUseReadData(PointHistoryDTO dto){ // 반환 타입을 List로 변경합니다.
        return pointHistoryMapper.getUseReadData(dto);
    }

}
