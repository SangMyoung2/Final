package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.GatchiLikeDTO;

public interface GatchiService {

	//BoardMapper로 연결된다.
	
	public void createGatchi(GatchiDTO dto) throws Exception;

	public int maxNum() throws Exception;
	
	public GatchiDTO getReadData(int meetListNum) throws Exception;

	public List<GatchiDTO> getMeetMateLists() throws Exception;
	
	public List<GatchiDTO> getCommuniFindLists() throws Exception;
	
	public List<GatchiDTO> getMeetMateRandomList(int count) throws Exception;
	
	public List<GatchiDTO> getCommuniFindRandomList(int count) throws Exception;
	
	//public List<GatchiDTO> getMeetMateListsPaging(int start, int itemsPerPage) throws Exception;


	
	//public void meetLikes(int meetListNum) throws Exception;
    //public GatchiLikeDTO meetLikes(String userEmail, int meetListNum);

    // public interface GatchiService {
	// 	List<GatchiDTO> getMeetMateRandomList(int count);
	// }

	// public List<GatchiDTO> getMeetMateRandomList(int i);

	// public void insertData(BoardDTO dto) throws Exception;
	
	// public int getDataCount(String searchKey,String searchValue) throws Exception;
	
	// public List<BoardDTO> getLists(int start,int end,String searchKey,String searchValue) throws Exception;
	
	// public BoardDTO getReadData(int num) throws Exception;
	
	// public void updateHitCount(int num) throws Exception;
	
	// public void updateData(BoardDTO dto) throws Exception;
	
	// public void deleteData(int num) throws Exception;
	
}
