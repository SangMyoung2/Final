package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
