package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.MapDTO;

@Mapper
public interface MapMapper {

    public List<MapDTO> getLists() throws Exception;
}
