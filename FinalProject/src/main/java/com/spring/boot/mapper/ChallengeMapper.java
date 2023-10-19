package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.ChallengeDTO;

@Mapper
public interface ChallengeMapper {
    
    public int maxNum() throws Exception;
    
    public void createChallenge(ChallengeDTO dto) throws Exception;
	
	public ChallengeDTO getReadData(int meetListNum) throws Exception;

	public List<ChallengeDTO> getMeetMateLists() throws Exception;


}
