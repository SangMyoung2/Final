package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetDTOYj;

public interface MeetServiceYj {

    public List<MeetDTOYj> getAllCategories() throws Exception;

    public MeetDTOYj getMeetListInfo(int meetListNum) throws Exception;

    public List<MeetDTOYj> getReview(int meetListNum) throws Exception;

    public List<String> getMeetMembers(int meetListNum) throws Exception;

    public void insertMeetReview(MeetDTOYj dto) throws Exception;

    public int getReviewNum(int meetListNum) throws Exception;
    
    public void insertMeetJoin(MeetDTOYj dto) throws Exception;

    public List<String> getMeetBlack(int meetListNum) throws Exception;

    public void addToBlacklist(int meetListNum, String email) throws Exception;

    public void releaseFromBlacklist(int meetListNum, String email) throws Exception;

}
