package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.dto.MeetDTOYj;

@Mapper
public interface MeetMapperYj {

    public List<MeetDTOYj> getAllCategories() throws Exception;

    public MeetDTOYj getMeetListInfo(int meetListNum) throws Exception;

    public List<MeetDTOYj> getReview(int meetListNum) throws Exception;

    public List<String> getMeetMembers(int meetListNum) throws Exception;

    public void insertMeetReview(MeetDTOYj dto) throws Exception;

    public int getReviewNum(int meetListNum) throws Exception;
    
    public void insertMeetJoin(MeetDTOYj dto) throws Exception;

    public List<String> getMeetBlack(int meetListNum) throws Exception;

    public void addToBlacklist(MeetDTOYj dto) throws Exception;

    public void releaseFromBlacklist(MeetDTOYj dto) throws Exception;

}
