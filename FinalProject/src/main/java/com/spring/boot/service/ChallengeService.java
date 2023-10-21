package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;

public interface ChallengeService {
    
    public int maxNum() throws Exception;
    
    public void createChallenge(ChallengeDTO dto) throws Exception;
	
	public ChallengeDTO getReadData(int meetListNum) throws Exception;

	public List<ChallengeDTO> getChallengeLists() throws Exception;

    public void insertChallengeInfo(ChallengeInfoDTO infoDto) throws Exception;

    public ChallengeInfoDTO getUserEmailData(String email, int challengelistnum) throws Exception;

    public void test(ChallengeDTO dto) throws Exception;

}
