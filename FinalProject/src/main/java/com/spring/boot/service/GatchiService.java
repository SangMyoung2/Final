package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetInfoDTO;

public interface GatchiService {

	//BoardMapper로 연결된다.
	
	public void createGatchi(GatchiDTO dto) throws Exception;

	public void createMeetInfo(MeetInfoDTO infoDTO) throws Exception;

	public List<MeetInfoDTO> getMeetInfo() throws Exception;

	public List<Integer> getMeetListNumByUserEmail(String userEmail)throws Exception;

	public List<GatchiDTO> getGatchiByMeetMateListNums(List<Integer> meetListNums);

	public int maxNum() throws Exception;

	//방장프로필
	public String getProfileByUsers(int meetListNum) throws Exception;
	
	public List<GatchiDTO> getMeetMateLists() throws Exception;
	
	public List<GatchiDTO> getCommuniFindLists() throws Exception;
	
	public List<GatchiDTO> getMeetMateRandomList(int count) throws Exception;
	
	public List<GatchiDTO> getCommuniFindRandomList(int count) throws Exception;
	
	public List<GatchiDTO> getRownumList(int endList) throws Exception;
	
	public List<GatchiDTO> searchMeetMateList(String searchKey, String searchValue) throws Exception;
	
	public List<GatchiDTO> searchCommuniFindList(String searchKey, String searchValue) throws Exception;

	public void updateMeetStatusMate(GatchiDTO gatchiDTO) throws Exception;

	public void updateMeetStatusFind(GatchiDTO gatchiDTO) throws Exception;

	public List<GatchiDTO> sortByLikeCountMeet() throws Exception;

	public List<GatchiDTO> sortByHitCountMeet() throws Exception;

	public List<GatchiDTO> sortByDdayMeet() throws Exception;

	public List<GatchiDTO> sortByLikeCountFind() throws Exception;

	public List<GatchiDTO> sortByHitCountFind() throws Exception;

	public List<GatchiDTO> sortByDdayFind() throws Exception;

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
	

	public void plusMeetCount(int meetListNum) throws Exception;

	public void minusMeetCount(int meetListNum) throws Exception;

	public List<GatchiDTO> getReadDataInUser(String useremail) throws Exception;

	public List<GatchiDTO> getReadDataInListNum(int meetListNum) throws Exception;
	
	public void updateChatRoom(GatchiDTO dto) throws Exception;
}
