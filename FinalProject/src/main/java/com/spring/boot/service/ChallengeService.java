package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.ChallengeAuthDTO;
import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
import com.spring.boot.dto.MapDTO;


public interface ChallengeService {
    
    public int maxNum() throws Exception;
    
    public int authMaxNum() throws Exception;
    
    public void createChallenge(ChallengeDTO dto) throws Exception;
	
    public ChallengeAuthDTO getNoneAuthReview(ChallengeAuthDTO authDTO) throws Exception;

	public ChallengeDTO getReadData(int meetListNum) throws Exception;

	public List<ChallengeDTO> getChallengeLists() throws Exception;

    public void insertChallengeInfo(ChallengeInfoDTO infoDto) throws Exception;

    public ChallengeInfoDTO getUserEmailData(String email, int challengeListNum) throws Exception;

    public List<ChallengeInfoDTO> getUserListData(int challengeListNum) throws Exception;

    public List<ChallengeAuthDTO> getAllReviewList(int challengeListNum) throws Exception;
    
    public ChallengeInfoDTO getMasterData(int challengeListNum) throws Exception;
    
    public void deleteChallengeStatus(int challengeListNum) throws Exception;
    
    public void updateChallengeInfoStatus(int challengeMemberStatus, int challengeListNum, String email) throws Exception;
    
    public void deleteChallengeInfo(int challengeListNum, String email) throws Exception;

    public void test(ChallengeDTO dto) throws Exception;

    public void insertAuthReview(ChallengeAuthDTO authDTO) throws Exception;

    public void deleteChallengeReview(ChallengeAuthDTO authDTO) throws Exception;

    public Integer getMemberStatus(ChallengeInfoDTO infoDTO) throws Exception;
    
    public MapDTO getlatlng(int meetListNum) throws Exception;

    public void plusChallengeCount(int challengeListNum) throws Exception;

    public void minusChallengeCount(int challengeListNum) throws Exception;

    public List<ChallengeDTO> getListsSerchValue(String searchValue) throws Exception;
    public void successChallengeAuth(String challengeAuthImage) throws Exception;

    public void updateChallengeStatus() throws Exception;

    public void failChallengeAuth(String challengeAuthImage) throws Exception;

    public List<Integer> getChallengeListNumByUserEmail(String email)throws Exception;

    public List<ChallengeDTO> getChallengeByChallengeListNums(List<Integer> challengeListNum);

    public void updateChallengeMemCnt(int challengeListNum) throws Exception;

    public void downChallengeMemCnt(int challengeListNum) throws Exception;

    public int getChallengeDay(int challengeListNum) throws Exception;

    public List<ChallengeAuthDTO> getUserReview(int challengeListNum, String email) throws Exception;

}
