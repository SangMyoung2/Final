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
    
    public void insertMeetJoinOk(MeetDTOYj dto) throws Exception;

    public Integer getMemberStatus(MeetDTOYj dto) throws Exception;

    public List<String> getMeetWait(int meetListNum) throws Exception;

    // public void updateMeetWait(MeetDTOYj dto) throws Exception;

    public void acceptToWaitlist(MeetDTOYj dto) throws Exception;

    public void rejectFromWaitlist(MeetDTOYj dto) throws Exception;

    public List<String> getMeetBlack(int meetListNum) throws Exception;
    
    // public void updateMeetBlack(MeetDTOYj dto) throws Exception;

    public void addToBlacklist(MeetDTOYj dto) throws Exception;

    public void releaseFromBlacklist(MeetDTOYj dto) throws Exception;

}
