package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeLikeDTO;
import com.spring.boot.mapper.ChallengeLikeMapper;

@Service
public class ChallengeLikeServiceImpl implements ChallengeLikeService{

    @Autowired
    private ChallengeLikeMapper challengeLikeMapper;

    @Override
    public void insertChallengeLike(ChallengeLikeDTO dto) throws Exception{
        challengeLikeMapper.insertChallengeLike(dto);
    }

    @Override
	public void deleteChallengeLike(ChallengeLikeDTO dto) throws Exception{
        challengeLikeMapper.deleteChallengeLike(dto);
    }

    @Override
    public ChallengeLikeDTO getReadDataInChallengeLikeDTO(ChallengeLikeDTO dto) throws Exception{
        return challengeLikeMapper.getReadDataInChallengeLikeDTO(dto);
    }

    @Override
	public List<ChallengeLikeDTO> getReadDataChallengeLike(String useremail) throws Exception{
        return challengeLikeMapper.getReadDataChallengeLike(useremail);
    }

    @Override
    public List<Integer> getChallengeLikeNumByUserEmail(String email) throws Exception {
        
        return challengeLikeMapper.getChallengeLikeNumByUserEmail(email);
    }

    @Override
    public List<ChallengeDTO> getChallengeLikeNums(List<Integer> challengeListNum) {
        return challengeLikeMapper.getChallengeLikeNums(challengeListNum);
        
    }
}
