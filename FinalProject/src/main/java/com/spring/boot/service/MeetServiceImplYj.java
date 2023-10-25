package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.GatchiDTO;
import com.spring.boot.dto.MapDTO;
import com.spring.boot.dto.MeetCategoryDTO;
import com.spring.boot.dto.MeetInfoDTO;
import com.spring.boot.dto.MeetReviewDTO;
import com.spring.boot.mapper.MeetMapperYj;

@Service
public class MeetServiceImplYj implements MeetServiceYj {
    
    @Autowired
    private MeetMapperYj meetMapperYj;

    @Override
    public MeetInfoDTO getMeetMaster(int meetListNum) throws Exception {
        return meetMapperYj.getMeetMaster(meetListNum);
    }

    @Override
    public List<MeetCategoryDTO> getAllCategories() throws Exception {
        return meetMapperYj.getAllCategories();
    }

    @Override
    public GatchiDTO getMeetListInfo(int meetListNum) throws Exception {
        return meetMapperYj.getMeetListInfo(meetListNum);
    }
    
    @Override
    public List<MeetInfoDTO> getMeetMembers(int meetListNum) throws Exception {
        return meetMapperYj.getMeetMembers(meetListNum);
    }
    
    @Override
    public List<MeetInfoDTO> getMembersExMaster(int meetListNum) throws Exception {
        return meetMapperYj.getMembersExMaster(meetListNum);
    }
    
    @Override
    public List<MeetReviewDTO> getReview(int meetListNum) throws Exception {
        return meetMapperYj.getReview(meetListNum);
    }

    @Override
    public void insertMeetReview(MeetReviewDTO dto) throws Exception {
        meetMapperYj.insertMeetReview(dto);
    }

    @Override
    public int getReviewNum(int meetListNum) throws Exception {
        return meetMapperYj.getReviewNum(meetListNum);
    }
    
    @Override
    public int hasUserReviewed(MeetReviewDTO dto) throws Exception {
        return meetMapperYj.hasUserReviewed(dto);
    }
    
    @Override
    public void deleteMeetReview(MeetReviewDTO dto) throws Exception {
        meetMapperYj.deleteMeetReview(dto);
    }

    @Override
    public Integer getMemberStatus(MeetInfoDTO dto) throws Exception {
        return meetMapperYj.getMemberStatus(dto);
    }

    @Override
    public Integer getApprovalStatus(MeetInfoDTO dto) throws Exception {
        return meetMapperYj.getApprovalStatus(dto);
    }

    @Override
    public int getMeetHow(int meetListNum) throws Exception {
        return meetMapperYj.getMeetHow(meetListNum);
    }
    
    @Override
    public void insertMeetJoinOk(MeetInfoDTO dto) throws Exception {
        System.out.println("inserMeetJoin 들어옴 ===");
        meetMapperYj.insertMeetJoinOk(dto);
    }

    @Override
    public void deleteMeetOut(MeetInfoDTO dto) throws Exception {
        meetMapperYj.deleteMeetOut(dto);
    }

    @Override
    public void updateMeetStatus(GatchiDTO dto) throws Exception {
        meetMapperYj.updateMeetStatus(dto);
    }

    @Override
    public void meetStatusCompletion(GatchiDTO dto) throws Exception {
        meetMapperYj.meetStatusCompletion(dto);
    }

    @Override
    public int getMeetStatus(int meetListNum) throws Exception {
        return meetMapperYj.getMeetStatus(meetListNum);
    }

    @Override
    public List<MeetInfoDTO> getMeetWait(int meetListNum) throws Exception {
        return meetMapperYj.getMeetWait(meetListNum);
    }

    @Override
    public int acceptToWaitlist(MeetInfoDTO dto) throws Exception {
        return meetMapperYj.acceptToWaitlist(dto);
    }

    @Override
    public void incrementMeetMemCnt(int meetListNum) throws Exception {
        meetMapperYj.incrementMeetMemCnt(meetListNum);
    }

    @Override
    public void rejectFromWaitlist(MeetInfoDTO dto) throws Exception {
        meetMapperYj.rejectFromWaitlist(dto);
    }

    @Override
    public List<MeetInfoDTO> getMeetBlack(int meetListNum) throws Exception {
        return meetMapperYj.getMeetBlack(meetListNum);
    }

    @Override
    public void addToBlacklist(MeetInfoDTO dto) throws Exception {
        meetMapperYj.addToBlacklist(dto);
    }

    @Override
    public void decrementMeetMemCnt(int meetListNum) throws Exception {
        meetMapperYj.decrementMeetMemCnt(meetListNum);
    }

    @Override
    public void releaseFromBlacklist(MeetInfoDTO dto) throws Exception {
        meetMapperYj.releaseFromBlacklist(dto);
    }
    
    @Override
    public void updateApprovalReq(MeetInfoDTO dto) throws Exception {
        meetMapperYj.updateApprovalReq(dto);
    }
    
    @Override
    public void updateApprovalOk(MeetInfoDTO dto) throws Exception {
        meetMapperYj.updateApprovalOk(dto);
    }
    
    @Override
    public void updateReject(MeetInfoDTO dto) throws Exception {
        meetMapperYj.updateReject(dto);
    }

    @Override
    public List<MeetInfoDTO> getMeetInfo(int meetListNum) throws Exception{
        return meetMapperYj.getMeetInfo(meetListNum);
    }

     @Override
    public MapDTO getlatlng(int meetListNum) throws Exception {
        return meetMapperYj.getlatlng(meetListNum);
    }
}
