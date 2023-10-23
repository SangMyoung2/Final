package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetCalculateDTO;

public interface MeetCalculateService {
    
    public void insertData(MeetCalculateDTO dto) throws Exception;
    public List<MeetCalculateDTO> getLists(int meetListNum) throws Exception;
    public void updateStatus(MeetCalculateDTO dto) throws Exception;

}
