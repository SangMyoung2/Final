package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.ChallengeLikeDTO;

@Mapper
public interface ChallengeLikeMapper {
    public void insertChallengeLike(ChallengeLikeDTO dto) throws Exception;
	public void deleteChallengeLike(ChallengeLikeDTO dto) throws Exception;
    public ChallengeLikeDTO getReadDataInChallengeLikeDTO(ChallengeLikeDTO dto) throws Exception;
	public List<ChallengeLikeDTO> getReadDataChallengeLike(String useremail) throws Exception;
}
