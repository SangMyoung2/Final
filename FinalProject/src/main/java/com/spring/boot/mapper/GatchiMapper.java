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
	
    //public void meetLikes(int meetListNum) throws Exception;

	
	// public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	// public List<MeetmateDTO> getLists(int start, int end, String searchKey, String searchValue);
		
	// public void updateHitCount(int num) throws Exception;	
	
	// public void updateData(MeetmateDTO dto) throws Exception;
	
	// public void deleteData(int num) throws Exception;
    
}
