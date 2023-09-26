package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MapDTO;

public interface MapService {
    
    public List<MapDTO> getLists() throws Exception;
}
