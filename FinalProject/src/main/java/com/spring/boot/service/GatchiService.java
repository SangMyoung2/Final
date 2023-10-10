package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.GatchiDTO;

public interface GatchiService {

	//BoardMapper로 연결된다.
	
	public void createMeetmate(GatchiDTO dto) throws Exception;

	public int maxNum() throws Exception;
	
	public List<GatchiDTO> getMeetLists() throws Exception;
	// public void insertData(BoardDTO dto) throws Exception;
	
	// public int getDataCount(String searchKey,String searchValue) throws Exception;
	
	// public List<BoardDTO> getLists(int start,int end,String searchKey,String searchValue) throws Exception;
	
	// public BoardDTO getReadData(int num) throws Exception;
	
	// public void updateHitCount(int num) throws Exception;
	
	// public void updateData(BoardDTO dto) throws Exception;
	
	// public void deleteData(int num) throws Exception;
	
}
