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
	
	public List<GatchiDTO> getRownumList(int endList) throws Exception;
	public List<GatchiDTO> searchMeetMateList(String searchKey, String searchValue) throws Exception;

	public void plusMeetCount(int meetListNum) throws Exception;
	public void minusMeetCount(int meetListNum) throws Exception;
	public List<GatchiDTO> getReadDataInUser(String useremail) throws Exception;
	public List<GatchiDTO> getReadDataInListNum(int meetListNum) throws Exception;

}
