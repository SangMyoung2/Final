package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetInfoDTO;

@Mapper
public interface GatchiMapper {
	
	public void createGatchi(GatchiDTO dto) throws Exception;

	public void createMeetInfo(MeetInfoDTO dto) throws Exception;

	public List<MeetInfoDTO> getMeetInfo()throws Exception;

	public List<Integer> getMeetListNumByUserEmail(String email)throws Exception;

	public List<Integer> getMeetLikeNumByUserEmail(String email)throws Exception;

	public List<GatchiDTO> getGatchiByLikeNums(List<Integer> meetListNums);

	public List<GatchiDTO> getGatchiByMeetMateListNums(List<Integer> meetListNums);

	public List<GatchiDTO> getGatchiByMeetcommuListNums(List<Integer> meetListNums);

	public int maxNum() throws Exception;

	public String masterPicture() throws Exception;
	
	public GatchiDTO getReadData(int meetListNum) throws Exception;

	public List<GatchiDTO> getMeetMateLists() throws Exception;

	public List<GatchiDTO> getCommuniFindLists() throws Exception;

	public List<GatchiDTO> getMeetMateRandomList(int count) throws Exception;

	public List<GatchiDTO> getCommuniFindRandomList(int count) throws Exception;

    public List<GatchiDTO> searchMeetMateList(@Param("searchKey")String searchKey, @Param("searchValue")String searchValue);

    public List<GatchiDTO> searchCommuniFindList(@Param("searchKey")String searchKey, @Param("searchValue")String searchValue);

	public List<GatchiDTO> getRownumList(int end) throws Exception;

    
	
	public List<GatchiDTO> sortByLikeCountMeet() throws Exception;

	public List<GatchiDTO> sortByHitCountMeet() throws Exception;

	public List<GatchiDTO> sortByDdayMeet() throws Exception;

	public List<GatchiDTO> sortByLikeCountFind() throws Exception;

	public List<GatchiDTO> sortByHitCountFind() throws Exception;

	public List<GatchiDTO> sortByDdayFind() throws Exception;

	public void updateMeetStatusMate(GatchiDTO gatchiDTO) throws Exception;

	public void updateMeetStatusFind(GatchiDTO gatchiDTO) throws Exception;

	
    //public void meetLikes(int meetListNum) throws Exception;

	// public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	// public List<MeetmateDTO> getLists(int start, int end, String searchKey, String searchValue);
		
	// public void updateHitCount(int meetListNum) throws Exception;	
	
	// public void updateData(MeetmateDTO dto) throws Exception;
	
	// public void deleteData(int num) throws Exception;


	public void plusMeetCount(int meetListNum) throws Exception;
    
	public void minusMeetCount(int meetListNum) throws Exception;

	public List<GatchiDTO> getReadDataInUser(String useremail) throws Exception;

	public List<GatchiDTO> getReadDataInListNum(int meetListNum) throws Exception;
	
	public void updateChatRoom(GatchiDTO dto) throws Exception;
}
