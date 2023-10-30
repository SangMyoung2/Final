package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.ChallengeDTO;
import com.spring.boot.dto.ChallengeLikeDTO;


@Mapper
public interface ChallengeLikeMapper {
    public void insertChallengeLike(ChallengeLikeDTO dto) throws Exception;
	public void deleteChallengeLike(ChallengeLikeDTO dto) throws Exception;
    public ChallengeLikeDTO getReadDataInChallengeLikeDTO(ChallengeLikeDTO dto) throws Exception;
	public List<ChallengeLikeDTO> getReadDataChallengeLike(String useremail) throws Exception;
    public List<Integer> getChallengeLikeNumByUserEmail(String email)throws Exception;
    public List<ChallengeDTO> getGatchiByLikeNums(List<Integer> challengeListNum);
    public List<ChallengeDTO> getChallengeLikeNums(List<Integer> challengeListNum);
}
