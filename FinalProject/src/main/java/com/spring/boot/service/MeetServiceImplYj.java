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
    public MeetDTOYj getMeetListInfo(int meetListNum) throws Exception {
        return meetMapperYj.getMeetListInfo(meetListNum);
    }

    @Override
    public List<MeetDTOYj> getReview(int meetListNum) throws Exception {
        return meetMapperYj.getReview(meetListNum);
    }

    @Override
    public List<String> getMeetMembers(int meetListNum) throws Exception {
        return meetMapperYj.getMeetMembers(meetListNum);
    }

    @Override
    public void insertMeetReview(MeetDTOYj dto) throws Exception {
        meetMapperYj.insertMeetReview(dto);
    }

    @Override
    public int getReviewNum(int meetListNum) throws Exception {
        return meetMapperYj.getReviewNum(meetListNum);
    }
    
    @Override
    public void insertMeetJoinOk(MeetDTOYj dto) throws Exception {
        meetMapperYj.insertMeetJoinOk(dto);
    }

    @Override
    public List<String> getMeetBlack(int meetListNum) throws Exception {
        return meetMapperYj.getMeetBlack(meetListNum);
    }

    @Override
    public void updateMeetBlack(MeetDTOYj dto) throws Exception {
        meetMapperYj.updateMeetBlack(dto);
    }

    @Override
    public void addToBlacklist(int meetListNum, String email) throws Exception {
        MeetDTOYj dto = new MeetDTOYj();
        dto.setMeetListNum(meetListNum);
        dto.setEmail(email);
        dto.setMeetMemStatus(3); // 블랙리스트 상태로 설정

        meetMapperYj.updateMeetBlack(dto);
    }

    @Override
    public void releaseFromBlacklist(int meetListNum, String email) throws Exception {
        MeetDTOYj dto = new MeetDTOYj();
        dto.setMeetListNum(meetListNum);
        dto.setEmail(email);
        dto.setMeetMemStatus(2); // 회원 상태로 설정

        meetMapperYj.updateMeetBlack(dto);
    }

    @Override
    public Integer getMemberStatus(MeetDTOYj dto) throws Exception {
        return meetMapperYj.getMemberStatus(dto);
    }

}
