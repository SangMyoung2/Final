package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;


@Mapper
public interface MapMapper {

    public List<MapDTO> getLists() throws Exception;

    public GatchiDTO getOneData(int meetListNum) throws Exception;

    public List<GatchiDTO> getData() throws Exception;

    public List<GatchiDTO> getTitleData(String meetTitle) throws Exception;

    public MeetCategoryDTO getCategory(int meetCtgNum) throws Exception;
    
    public void insertMapData(MapDTO dto) throws Exception;

}
