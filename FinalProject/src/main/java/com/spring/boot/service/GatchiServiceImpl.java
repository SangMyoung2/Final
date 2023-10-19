package com.spring.boot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.GatchiLikeDTO;
import com.spring.boot.mapper.GatchiMapper;

@Service
public class GatchiServiceImpl implements GatchiService{

	//BoardService로 연결한다.
	
	@Autowired //의존성 주입
	private GatchiMapper gatchiMapper;

	@Override
	public void createGatchi(GatchiDTO dto) throws Exception {		
		gatchiMapper.createGatchi(dto);
	}

	@Override
	public int maxNum() throws Exception {
		return gatchiMapper.maxNum();
	}
	
	@Override
	public GatchiDTO getReadData(int meetListNum) throws Exception {
		return gatchiMapper.getReadData(meetListNum);	
	}

	@Override
	public List<GatchiDTO> getMeetMateLists() throws Exception{
		return gatchiMapper.getMeetMateLists();
	}

	@Override
	public List<GatchiDTO> getCommuniFindLists() throws Exception{
		return gatchiMapper.getCommuniFindLists();
	}

	@Override
	public List<GatchiDTO> getMeetMateRandomList(int count) throws Exception {
		return gatchiMapper.getMeetMateRandomList(count);
	}

	@Override
	public List<GatchiDTO> getCommuniFindRandomList(int count) throws Exception {
		return gatchiMapper.getCommuniFindRandomList(count);
	}

	@Override
	public List<GatchiDTO> getRownumList(int end) throws Exception {
		return gatchiMapper.getRownumList(end);
	}

	@Override
	public List<GatchiDTO> searchMeetMateList(String searchKey, String searchValue) throws Exception {
		return gatchiMapper.searchMeetMateList(searchKey, searchValue);
	}

	@Override
	public void plusMeetCount(int meetListNum) throws Exception {
		gatchiMapper.plusMeetCount(meetListNum);
	}

	@Override
	public void minusMeetCount(int meetListNum) throws Exception{
		gatchiMapper.minusMeetCount(meetListNum);
	}

	@Override
	public List<GatchiDTO> getReadDataInUser(String useremail) throws Exception{
		return gatchiMapper.getReadDataInUser(useremail);
	}

	@Override
	public List<GatchiDTO> getReadDataInListNum(int meetListNum) throws Exception{
		return gatchiMapper.getReadDataInListNum(meetListNum);
	}

	


















}


