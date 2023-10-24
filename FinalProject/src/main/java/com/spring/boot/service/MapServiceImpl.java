package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.mapper.MapMapper;

@Service
public class MapServiceImpl implements MapService{

    @Autowired
    private MapMapper mapMapper;

    @Override
    public List<MapDTO> getLists() throws Exception {
        return mapMapper.getLists();
    }

    @Override
    public GatchiDTO getOneData(int meetListNum) throws Exception {
        return mapMapper.getOneData(meetListNum);
    }

    @Override
    public List<GatchiDTO> getData() throws Exception {
        return mapMapper.getData();
    }

    @Override
    public List<GatchiDTO> getTitleData(String meetTitle) throws Exception {
        return mapMapper.getTitleData(meetTitle);
    }

    @Override
    public MeetCategoryDTO getCategory(int meetCtgNum) throws Exception {
        return mapMapper.getCategory(meetCtgNum);
    }

    @Override
    public void insertMapData(MapDTO dto) throws Exception {
        mapMapper.insertMapData(dto);
    }

}
