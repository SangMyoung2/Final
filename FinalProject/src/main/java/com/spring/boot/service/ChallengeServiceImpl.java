package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeInfoDTO;
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
    public ChallengeInfoDTO getUserEmailData(String email, int challengelistnum) throws Exception {
        return challengeMapper.getUserEmailData(email,challengelistnum);
    }

    @Override
    public ChallengeInfoDTO getMasterData(int challengelistnum) throws Exception {
        return challengeMapper.getMasterData(challengelistnum); 
    }

    
}
