package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.ChallengeAuthDTO;
import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.mapper.ChallengeMapper;

@Service
public class ChallengeServiceImpl implements ChallengeService{
    
    @Autowired
    private ChallengeMapper challengeMapper;

    @Override
    public int maxNum() throws Exception {
        return challengeMapper.maxNum();
    }

    @Override
    public void createChallenge(ChallengeDTO dto) throws Exception {
        challengeMapper.createChallenge(dto);
    }

    @Override
    public ChallengeDTO getReadData(int meetListNum) throws Exception {
        return challengeMapper.getReadData(meetListNum);
    }

    @Override
    public List<ChallengeDTO> getChallengeLists() throws Exception {
        return challengeMapper.getChallengeLists();
    }

    @Override
    public void insertChallengeInfo(ChallengeInfoDTO infoDto) throws Exception {
        challengeMapper.insertChallengeInfo(infoDto);
    }

    @Override
    public void test(ChallengeDTO dto) throws Exception {
        challengeMapper.test(dto);
    }

    @Override
    public ChallengeInfoDTO getUserEmailData(String email, int challengeListNum) throws Exception {
        return challengeMapper.getUserEmailData(email,challengeListNum);
    }

    @Override
    public ChallengeInfoDTO getMasterData(int challengeListNum) throws Exception {
        return challengeMapper.getMasterData(challengeListNum); 
    }

    @Override
    public void deleteChallengeStatus(int challengeListNum) throws Exception {
        challengeMapper.deleteChallengeStatus(challengeListNum);
    }

    @Override
    public void updateChallengeInfoStatus(int challengeMemberStatus, int challengeListNum, String email)
            throws Exception {
        challengeMapper.updateChallengeInfoStatus(challengeMemberStatus, challengeListNum, email);
    }

    @Override
    public void deleteChallengeInfo(int challengeListNum, String email) throws Exception {
        challengeMapper.deleteChallengeInfo(challengeListNum, email);
    }

    @Override
    public List<ChallengeInfoDTO> getUserListData(int challengeListNum) throws Exception {
        return challengeMapper.getUserListData(challengeListNum);
    }

    @Override
    public int authMaxNum() throws Exception {
        return challengeMapper.authMaxNum();
    }

    @Override
    public ChallengeAuthDTO getNoneAuthReview(ChallengeAuthDTO authDTO) throws Exception {
        return challengeMapper.getNoneAuthReview(authDTO);
    }

    @Override
    public void insertAuthReview(ChallengeAuthDTO authDTO) throws Exception {
        challengeMapper.insertAuthReview(authDTO);
    }

    @Override
    public List<ChallengeAuthDTO> getAllReviewList(int challengeListNum) throws Exception {
        return challengeMapper.getAllReviewList(challengeListNum);
    }

    @Override
    public Integer getMemberStatus(ChallengeInfoDTO infoDTO) throws Exception {
        return challengeMapper.getMemberStatus(infoDTO);
    }

    @Override
    public void deleteChallengeReview(ChallengeAuthDTO authDTO) throws Exception {
        challengeMapper.deleteChallengeReview(authDTO);
    }

    @Override
    public MapDTO getlatlng(int meetListNum) throws Exception {
        return challengeMapper.getlatlng(meetListNum);
    }

    @Override
    public void plusChallengeCount(int challengeListNum) throws Exception{
        challengeMapper.plusChallengeCount(challengeListNum);
    }

    @Override
    public void minusChallengeCount(int challengeListNum) throws Exception{
        challengeMapper.minusChallengeCount(challengeListNum);
    }

    @Override
    public List<ChallengeDTO> getListsSerchValue(String searchValue) throws Exception{
        return challengeMapper.getListsSerchValue(searchValue);
    }
    public void successChallengeAuth(String challengeAuthImage) throws Exception {
        challengeMapper.successChallengeAuth(challengeAuthImage);
    }

    @Override
    public void updateChallengeStatus() throws Exception {
        challengeMapper.updateChallengeStatus();
    }

    @Override
    public void failChallengeAuth(String challengeAuthImage) throws Exception {
        challengeMapper.failChallengeAuth(challengeAuthImage);
    }

    @Override
    public List<Integer> getChallengeListNumByUserEmail(String email) throws Exception {
       return challengeMapper.getChallengeListNumByUserEmail(email);
    }

    @Override
	public List<ChallengeDTO> getChallengeByChallengeListNums(List<Integer> challengeListNum) {
        return challengeMapper.getChallengeByChallengeListNums(challengeListNum);
	}

    @Override
    public void updateChallengeMemCnt(int challengeListNum) throws Exception {
        challengeMapper.updateChallengeMemCnt(challengeListNum);
    }

    @Override
    public void downChallengeMemCnt(int challengeListNum) throws Exception {
        challengeMapper.downChallengeMemCnt(challengeListNum);
    }

    @Override
    public int getChallengeDay(int challengeListNum) throws Exception {
        return challengeMapper.getChallengeDay(challengeListNum);
    }

    @Override
    public List<ChallengeAuthDTO> getUserReview(int challengeListNum, String email) throws Exception {
        return challengeMapper.getUserReview(challengeListNum, email);
    }
}
