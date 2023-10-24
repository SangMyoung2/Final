package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;


public interface MapService {
    
    public List<MapDTO> getLists() throws Exception;

    public GatchiDTO getOneData(int meetListNum) throws Exception;

    public List<GatchiDTO> getData() throws Exception;
    
    public List<GatchiDTO> getTitleData(String meetTitle) throws Exception;

    public MeetCategoryDTO getCategory(int meetCtgNum) throws Exception;

    public void insertMapData(MapDTO dto) throws Exception;
}
