package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MeetInfoDTO;
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
	public void createCommuni(GatchiDTO dto) throws Exception {		
		gatchiMapper.createCommuni(dto);
	}
	
	@Override
	public void createMeetInfo(MeetInfoDTO infoDTO) throws Exception {
		gatchiMapper.createMeetInfo(infoDTO);
	}

	
	@Override
	public List<Integer> getMeetListNumByUserEmail(String email) throws Exception {
		return gatchiMapper.getMeetListNumByUserEmail(email);
	}

	@Override
	public List<Integer> getMeetLikeNumByUserEmail(String email) throws Exception {
		return gatchiMapper.getMeetLikeNumByUserEmail(email);
	}

	@Override
	public List<GatchiDTO> getGatchiByLikeNums(List<Integer> meetListNums) {
        return gatchiMapper.getGatchiByLikeNums(meetListNums);
	}

	@Override
	public List<GatchiDTO> getGatchiByMeetMateListNums(List<Integer> meetListNums) {
        return gatchiMapper.getGatchiByMeetMateListNums(meetListNums);
	}

	@Override
	public List<GatchiDTO> getGatchiByMeetcommuListNums(List<Integer> meetListNums) {
        return gatchiMapper.getGatchiByMeetcommuListNums(meetListNums);
	}


	@Override
	public List<MeetInfoDTO> getMeetInfo() throws Exception {
		
		return gatchiMapper.getMeetInfo();
	}

	@Override
	public int maxNum() throws Exception {
		return gatchiMapper.maxNum();
	}
	
	
	@Override
	public String masterPicture() throws Exception {
		return gatchiMapper.masterPicture();
	}

	// @Override
	// public GatchiDTO getReadData(int meetListNum) throws Exception {
	// 	return gatchiMapper.getReadData(meetListNum);	
	// }

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
	public List<GatchiDTO> searchCommuniFindList(String searchKey, String searchValue) throws Exception {
		return gatchiMapper.searchCommuniFindList(searchKey, searchValue);
	}
	
	@Override
	public void updateMeetStatusMate(GatchiDTO gatchiDTO) throws Exception {
		gatchiMapper.updateMeetStatusMate(gatchiDTO);
	}
	
	@Override
	public void updateMeetStatusFind(GatchiDTO gatchiDTO) throws Exception {
		gatchiMapper.updateMeetStatusFind(gatchiDTO);
	}
	
	@Override
	public List<GatchiDTO> sortByLikeCountMeet() throws Exception {
		return gatchiMapper.sortByLikeCountMeet();
	}
		
	@Override
	public List<GatchiDTO> sortByHitCountMeet() throws Exception {
		return gatchiMapper.sortByHitCountMeet();
	}
	
	@Override
	public List<GatchiDTO> sortByDdayMeet() throws Exception {
		return gatchiMapper.sortByDdayMeet();
	}
	
	@Override
	public List<GatchiDTO> sortByLikeCountFind() throws Exception {
		return gatchiMapper.sortByLikeCountFind();
	}
		
	@Override
	public List<GatchiDTO> sortByHitCountFind() throws Exception {
		return gatchiMapper.sortByHitCountFind();
	}
	
	@Override
	public List<GatchiDTO> sortByDdayFind() throws Exception {
		return gatchiMapper.sortByDdayFind();
	}

	@Override
   public void updateHitCount(int meetListNum) throws Exception {
      gatchiMapper.updateHitCount(meetListNum);
   }


/*
	@Override
	public void meetLikes(int meetListNum) throws Exception {
		gatchiMapper.meetLikes(meetListNum);
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

@Override
public void updateChatRoom(GatchiDTO dto) throws Exception{
	gatchiMapper.updateChatRoom(dto);
}

	@Override
	public String getProfileByUsers(int meetListNum) throws Exception {
		return gatchiMapper.getProfileByUsers(meetListNum);
	}
}


