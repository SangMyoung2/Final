package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.TestBoardDTO;

public interface MapService {
    
    public List<MapDTO> getLists() throws Exception;

    public TestBoardDTO getOneData(int listNum) throws Exception;

    public List<TestBoardDTO> getData() throws Exception;
    
    public List<TestBoardDTO> getTitleData(String title) throws Exception;
}
