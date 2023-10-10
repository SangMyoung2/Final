package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.TestBoardDTO;

@Mapper
public interface MapMapper {

    public List<MapDTO> getLists() throws Exception;

    public TestBoardDTO getOneData(int listNum) throws Exception;

    public List<TestBoardDTO> getData() throws Exception;

    public List<TestBoardDTO> getTitleData(String title) throws Exception;
}
