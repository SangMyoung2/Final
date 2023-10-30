package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.PointHistoryDTO;

public interface PointHistoryService {
    public void insertPointHistory(PointHistoryDTO dto);
    public List<PointHistoryDTO> getUseReadData(PointHistoryDTO dto);

}
