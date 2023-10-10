package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetDTOYj;

public interface MeetServiceYj {

    public List<MeetDTOYj> getAllCategories() throws Exception;

    public MeetDTOYj getMeetInfo(int meet_listnum) throws Exception;

    public List<MeetDTOYj> getReview(int meet_listnum) throws Exception;

    public List<String> getMeetMembers(int meet_listnum) throws Exception;

    public void insertMeetReview(MeetDTOYj dto) throws Exception;

    public int getReviewNum(int meet_listnum) throws Exception;

}
