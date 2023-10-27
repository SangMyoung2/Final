package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.dto.ChallengeAuthDTO;
import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
import com.spring.boot.dto.MapDTO;

@Mapper
public interface ChallengeMapper {
    
    public int maxNum() throws Exception;
    
    public int authMaxNum() throws Exception;

    public void createChallenge(ChallengeDTO dto) throws Exception;
	
	public ChallengeDTO getReadData(int meetListNum) throws Exception;

	public ChallengeAuthDTO getNoneAuthReview(ChallengeAuthDTO authDTO) throws Exception;

	public List<ChallengeDTO> getChallengeLists() throws Exception;

    public List<ChallengeAuthDTO> getAllReviewList(int challengeListNum) throws Exception;

    public void insertChallengeInfo(ChallengeInfoDTO infoDto) throws Exception;

    public ChallengeInfoDTO getUserEmailData(@Param("email")String email,@Param("challengeListNum")int challengeListNum) throws Exception;

    public List<ChallengeInfoDTO> getUserListData(@Param("challengeListNum")int challengeListNum) throws Exception;

    public ChallengeInfoDTO getMasterData(int challengeListNum) throws Exception;

    public void deleteChallengeStatus(int challengeListNum) throws Exception;

    public void updateChallengeInfoStatus(@Param("challengeMemberStatus")int challengeMemberStatus, @Param("challengeListNum")int challengeListNum, @Param("email")String email) throws Exception;

    public void deleteChallengeInfo(@Param("challengeListNum")int challengeListNum, @Param("email")String email) throws Exception;
    
    public void test(ChallengeDTO dto) throws Exception;

    public void insertAuthReview(ChallengeAuthDTO authDTO) throws Exception;

    public Integer getMemberStatus(ChallengeInfoDTO infoDTO) throws Exception;

    public void deleteChallengeReview(ChallengeAuthDTO authDTO) throws Exception;

    public MapDTO getlatlng(int meetListNum) throws Exception;

    public void successChallengeAuth(String challengeAuthImage) throws Exception;

    public void updateChallengeStatus() throws Exception;
    
    public void failChallengeAuth() throws Exception;
}
