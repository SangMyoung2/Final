package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.MeetCalculateDTO;
import com.spring.boot.mapper.MeetCalculateMapper;

@Service
public class MeetCalculateServiceImpl implements MeetCalculateService{
    
    @Autowired
    private MeetCalculateMapper meetCalculateMapper;
    
    @Override
    public void insertData(MeetCalculateDTO dto) throws Exception{
        meetCalculateMapper.insertData(dto);
    }
    @Override
    public List<MeetCalculateDTO> getLists(int meetListNum) throws Exception{
        return meetCalculateMapper.getLists(meetListNum);
    }

    @Override
    public void updateStatus(MeetCalculateDTO dto) throws Exception{
        meetCalculateMapper.updateStatus(dto);
    }
}
