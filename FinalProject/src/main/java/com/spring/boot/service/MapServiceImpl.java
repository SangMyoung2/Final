package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.TestBoardDTO;
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
    public TestBoardDTO getOneData(int listNum) throws Exception {
        return mapMapper.getOneData(listNum);
    }

    
    
}
