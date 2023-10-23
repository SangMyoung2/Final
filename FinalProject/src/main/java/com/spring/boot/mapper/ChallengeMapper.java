package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;

@Mapper
public interface ChallengeMapper {
    
    public int maxNum() throws Exception;
    
    public void createChallenge(ChallengeDTO dto) throws Exception;
	
	public ChallengeDTO getReadData(int meetListNum) throws Exception;

	public List<ChallengeDTO> getChallengeLists() throws Exception;

    public void insertChallengeInfo(ChallengeInfoDTO infoDto) throws Exception;

    public ChallengeInfoDTO getUserEmailData(@Param("email")String email,@Param("challengelistnum")int challengelistnum) throws Exception;

    public ChallengeInfoDTO getMasterData(int challengelistnum) throws Exception;

    public void test(ChallengeDTO dto) throws Exception;

    

}
