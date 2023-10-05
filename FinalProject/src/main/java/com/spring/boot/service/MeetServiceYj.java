package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.MeetDTOYj;

public interface MeetServiceYj {

    public List<MeetDTOYj> getAllCategories() throws Exception;

    public List<MeetDTOYj> getLists(int meet_listnum) throws Exception;

    public List<MeetDTOYj> getReview() throws Exception;

    public List<String> getMeetMembers(int meet_listnum) throws Exception;

    public void insertMeetReview(MeetDTOYj dto) throws Exception;
}
