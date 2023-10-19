package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.GatchiDTO;

@Mapper
public interface GatchiMapper {
	
	public void createGatchi(GatchiDTO dto) throws Exception;

	public int maxNum() throws Exception;
	
	public GatchiDTO getReadData(int meetListNum) throws Exception;

	public List<GatchiDTO> getMeetMateLists() throws Exception;

	public List<GatchiDTO> getCommuniFindLists() throws Exception;

	public List<GatchiDTO> getMeetMateRandomList(int count) throws Exception;

	public List<GatchiDTO> getCommuniFindRandomList(int count) throws Exception;

    public List<GatchiDTO> searchMeetMateList(String searchKey, String searchValue);

	public List<GatchiDTO> getRownumList(int end) throws Exception;
	
	public void plusMeetCount(int meetListNum) throws Exception;
    
	public void minusMeetCount(int meetListNum) throws Exception;

	public List<GatchiDTO> getReadDataInUser(String useremail) throws Exception;

	public List<GatchiDTO> getReadDataInListNum(int meetListNum) throws Exception;
	
}
