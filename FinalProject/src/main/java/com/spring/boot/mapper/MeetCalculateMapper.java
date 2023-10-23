package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.MeetCalculateDTO;

@Mapper
public interface MeetCalculateMapper {
    
    public void insertData(MeetCalculateDTO dto) throws Exception;
    public List<MeetCalculateDTO> getLists(int meetListNum) throws Exception;
    public void updateStatus(MeetCalculateDTO dto) throws Exception;


}
