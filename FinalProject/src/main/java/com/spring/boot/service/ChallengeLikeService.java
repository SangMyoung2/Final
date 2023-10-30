package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeLikeDTO;


public interface ChallengeLikeService {
    public void insertChallengeLike(ChallengeLikeDTO dto) throws Exception;
	public void deleteChallengeLike(ChallengeLikeDTO dto) throws Exception;
    public ChallengeLikeDTO getReadDataInChallengeLikeDTO(ChallengeLikeDTO dto) throws Exception;
	public List<ChallengeLikeDTO> getReadDataChallengeLike(String useremail) throws Exception;
    public List<Integer> getChallengeLikeNumByUserEmail(String email)throws Exception;
    public List<ChallengeDTO> getChallengeLikeNums(List<Integer> challengeListNum);

}
