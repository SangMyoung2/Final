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
    public String getMeetMasterEmail(int meetListNum) throws Exception {
        return meetMapperYj.getMeetMasterEmail(meetListNum);
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
    public int hasUserReviewed(MeetDTOYj dto) throws Exception {
        return meetMapperYj.hasUserReviewed(dto);
    }
    
    @Override
    public void deleteMeetReview(MeetDTOYj dto) throws Exception {
        meetMapperYj.deleteMeetReview(dto);
    }

    @Override
    public Integer getMemberStatus(MeetDTOYj dto) throws Exception {
        return meetMapperYj.getMemberStatus(dto);
    }
    
    @Override
    public void insertMeetJoinOk(MeetDTOYj dto) throws Exception {
        meetMapperYj.insertMeetJoinOk(dto);
    }

    @Override
    public List<String> getMeetWait(int meetListNum) throws Exception {
        return meetMapperYj.getMeetWait(meetListNum);
    }

    @Override
    public int acceptToWaitlist(MeetDTOYj dto) throws Exception {
        return meetMapperYj.acceptToWaitlist(dto);
    }

    @Override
    public void incrementMeetMemCnt(int meetListNum) throws Exception {
        meetMapperYj.incrementMeetMemCnt(meetListNum);
    }

    @Override
    public void rejectFromWaitlist(MeetDTOYj dto) throws Exception {
        meetMapperYj.rejectFromWaitlist(dto);
    }

    @Override
    public List<String> getMeetBlack(int meetListNum) throws Exception {
        return meetMapperYj.getMeetBlack(meetListNum);
    }

    @Override
    public void addToBlacklist(MeetDTOYj dto) throws Exception {
        meetMapperYj.addToBlacklist(dto);
    }

    @Override
    public void decrementMeetMemCnt(int meetListNum) throws Exception {
        meetMapperYj.decrementMeetMemCnt(meetListNum);
    }

    @Override
    public void releaseFromBlacklist(MeetDTOYj dto) throws Exception {
        meetMapperYj.releaseFromBlacklist(dto);
    }

}
