package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiDTO;
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
	public List<GatchiDTO> getMeetMateListsPaging(int start, int itemsPerPage) throws Exception {
		return gatchiMapper.getMeetMateListsPaging(start, itemsPerPage);
	}






/*
	@Override
	public void meetLikes(int meetListNum) throws Exception {
		gatchiMapper.meetLikes(meetListNum);
	}

	

	@Override
	public void updateHitCount(int num) throws Exception {
		boardMapper.updateHitCount(num);
	}



	@Override
	public void insertData(BoardDTO dto) throws Exception {
		
		boardMapper.insertData(dto);
		
	}

	@Override
	public int getDataCount(String searchKey, String searchValue) throws Exception {
		return boardMapper.getDataCount(searchKey, searchValue);
	}

	@Override
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue) throws Exception {
		return boardMapper.getLists(start, end, searchKey, searchValue);
	}

	@Override
	public BoardDTO getReadData(int num) throws Exception {
		return boardMapper.getReadData(num);
	}


	@Override
	public void updateData(BoardDTO dto) throws Exception {
		boardMapper.updateData(dto);
	}

	@Override
	public void deleteData(int num) throws Exception {
		boardMapper.deleteData(num);
	}
*/
}


