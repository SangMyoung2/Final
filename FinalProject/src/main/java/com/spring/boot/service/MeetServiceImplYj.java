package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.MeetDTOYj;
import com.spring.boot.mapper.MeetMapperYj;

@Service
public class MeetServiceImplYj implements MeetServiceYj {
    
    @Autowired
    private MeetMapperYj meetMapperYj;

    @Override
    public List<MeetDTOYj> getAllCategories() throws Exception {
    
        return meetMapperYj.getAllCategories();
    
    }

    @Override
    public List<MeetDTOYj> getLists(int meet_listnum) throws Exception {
        
        return meetMapperYj.getLists(meet_listnum);
    }

    @Override
    public List<MeetDTOYj> getReview() throws Exception {
        
        return meetMapperYj.getReview();
    }

    @Override
    public List<String> getMeetMembers(int meet_listnum) throws Exception {
        return meetMapperYj.getMeetMembers(meet_listnum);
    }

    @Override
    public void insertMeetReview(MeetDTOYj dto) throws Exception {
        meetMapperYj.insertMeetReview(dto);
    }

}
