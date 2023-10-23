package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.GatchiLikeDTO;

public interface GatchiLikeService {
    public void insertGatchiLike(GatchiLikeDTO dto) throws Exception;
	public void deleteGatchiLike(GatchiLikeDTO dto) throws Exception;
	public GatchiLikeDTO getReadDataInLike(GatchiLikeDTO dto) throws Exception;
	public List<GatchiLikeDTO> getReadDataGatchiLike(String useremail) throws Exception;
}
