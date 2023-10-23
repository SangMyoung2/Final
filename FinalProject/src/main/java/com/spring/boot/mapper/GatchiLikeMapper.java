package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.GatchiLikeDTO;

@Mapper
public interface GatchiLikeMapper {
    
    public void insertGatchiLike(GatchiLikeDTO dto) throws Exception;
	public void deleteGatchiLike(GatchiLikeDTO dto) throws Exception;
    public GatchiLikeDTO getReadDataInLike(GatchiLikeDTO dto) throws Exception;
	public List<GatchiLikeDTO> getReadDataGatchiLike(String useremail) throws Exception;


}
