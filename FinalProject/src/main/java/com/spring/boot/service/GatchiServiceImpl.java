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
	public void createMeetInfo(MeetInfoDTO infoDTO) throws Exception {
		gatchiMapper.createMeetInfo(infoDTO);
	}

	// @Override
	// public List<Integer> getMeetListNumByUserEmail(String userEmail) throws Exception {
		
	// 	return gatchiMapper.getMeetListNumByUserEmail(userEmail);
	// }

	@Override
	public List<GatchiDTO> getGatchiByMeetListNums(List<Integer> meetListNums) {
        return gatchiMapper.getGatchiByMeetListNums(meetListNums);
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
	public String getProfileByUsers(int meetListNum) throws Exception {
		return gatchiMapper.getProfileByUsers(meetListNum);//방장프로필띄우기
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


}


