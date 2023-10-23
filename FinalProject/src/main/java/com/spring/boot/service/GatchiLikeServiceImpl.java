package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiLikeDTO;
import com.spring.boot.mapper.GatchiLikeMapper;

@Service
public class GatchiLikeServiceImpl implements GatchiLikeService{
    
    @Autowired
    private GatchiLikeMapper gatchiLikeMapper;

    @Override
	public void insertGatchiLike(GatchiLikeDTO dto) throws Exception{
		
		gatchiLikeMapper.insertGatchiLike(dto);
	}

	@Override
	public void deleteGatchiLike(GatchiLikeDTO dto) throws Exception{
		gatchiLikeMapper.deleteGatchiLike(dto);
	}

	@Override
	public GatchiLikeDTO getReadDataInLike(GatchiLikeDTO dto) throws Exception{
		return gatchiLikeMapper.getReadDataInLike(dto);
	}

	@Override
	public List<GatchiLikeDTO> getReadDataGatchiLike(String useremail) throws Exception{
		return gatchiLikeMapper.getReadDataGatchiLike(useremail);
	}


}
